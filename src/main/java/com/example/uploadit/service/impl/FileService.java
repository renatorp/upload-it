package com.example.uploadit.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import com.example.uploadit.component.IFileHelper;
import com.example.uploadit.component.IFileMetadataHandler;
import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.service.IFileService;
import com.example.uploadit.store.IInMemeoryDataStore;
import com.example.uploadit.vo.FileRequestBody;

@Service
public class FileService implements IFileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	private static final String CHUNK_PATH_FORMAT = "%s/%s/%s.tmp";
	private static final String FILE_PATH_FORMAT = "%s/%s/%s";

	@Value("${upload-it.chunks.dir}")
	private String chunksDir;
	
	@Value("${upload-it.files.dir}")
	private String filesDir;
	
	@Autowired
	private IInMemeoryDataStore dataStore;

	@Autowired
	private IFileMetadataHandler fileMetadataHandler;

	@Autowired
	private IFileHelper fileHelper;

	/**
	 * Armazena chunks de arquivos que foram subidos, assim como seus metadados.
	 */
	@Override
	public void uploadFile(FileRequestBody requestBody, String userId) {

		FileMetadata metadata = getFileMetadata(requestBody, userId);

		if (fileMetadataHandler.isNew(metadata)) {
			fileMetadataHandler.markAsProcessStarted(metadata);
		}

		storeContent(requestBody, metadata);
	}

	/**
	 * Se o upload for de chunks, o armazena. Caso contrário, armazena o arquivo completo e finaliza o processo de upload.
	 */
	private void storeContent(FileRequestBody requestBody, FileMetadata metadata) {
		
		if (fileMetadataHandler.isChunked(metadata)) {
			
			storeChunk(requestBody.getFile(), requestBody.getDzchunkindex(), metadata.getId());
			
			fileMetadataHandler.markChunkAsProcessed(metadata, requestBody.getDzchunkindex());
			
		} else {
			storeFile(requestBody.getFile(), metadata.getId());
			fileMetadataHandler.markAsProcessConcluded(metadata);
		}
		
	}

	private void storeFile(MultipartFile multipartFile, String fileId) {
		try {
			String fileName = String.format(FILE_PATH_FORMAT, filesDir, fileId, multipartFile.getOriginalFilename());
			fileHelper.storeMultipartFile(multipartFile, fileName);
		} catch (IOException e) {
			logger.error("An Unexpected Error Occurred.", e);
			throw new ServerErrorException("An Unexpected Error Occurred While Saving the File", e);
		}

	}

	private void storeChunk(MultipartFile multipartFile, Integer chunkIndex, String fileId) {

		try {
			String fileName = String.format(CHUNK_PATH_FORMAT, chunksDir, fileId, chunkIndex);
			fileHelper.storeMultipartFile(multipartFile, fileName);
		} catch (IOException e) {
			logger.error("An Unexpected Error Occurred.", e);
			throw new ServerErrorException("An Unexpected Error Occurred While Saving the File Chunk", e);
		}

	}

	/**
	 * Obtém metadados de arquivo. Se o arquivo for novo, cria um registro e o
	 * armazena/retorna.
	 */
	private FileMetadata getFileMetadata(FileRequestBody requestBody, String userId) {

		Optional<FileMetadata> uploadedFile = dataStore.findMetadataFileById(requestBody.getDzuuid());

		if (uploadedFile.isPresent()) {
			return uploadedFile.get();
		}

		FileMetadata metadata = fileMetadataHandler.createMetadata(requestBody, userId);
		dataStore.insertMetadataFile(metadata);
		return metadata;

	}

}
