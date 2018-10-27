package com.example.uploadit.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.uploadit.component.IFileHelper;
import com.example.uploadit.component.IFileMetadataHandler;
import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.exception.RestApplicationException;
import com.example.uploadit.service.IFileService;
import com.example.uploadit.store.IInMemeoryDataStore;
import com.example.uploadit.vo.FileRequestBody;

@Service
public class FileService implements IFileService {

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
			
			storeChunk(requestBody.getFile(), requestBody.getDzchunkindex(), metadata.getUserId());
			
			fileMetadataHandler.markChunkAsProcessed(metadata, requestBody.getDzchunkindex());
			
		} else {
			storeFile(requestBody.getFile(), metadata.getUserId());
			fileMetadataHandler.markAsProcessConcluded(metadata);
		}
		
	}

	private void storeFile(MultipartFile multipartFile, String userId) {
		try {
			String fileName = String.format(FILE_PATH_FORMAT, filesDir, userId, multipartFile.getOriginalFilename());
			fileHelper.storeMultipartFile(multipartFile, fileName);
		} catch (IOException e) {
			throw new RestApplicationException("An Unexpected Error Occurred While Saving the File", HttpStatus.INTERNAL_SERVER_ERROR, e);
		}

	}

	private void storeChunk(MultipartFile multipartFile, Integer chunkIndex, String userId) {

		try {
			String fileName = String.format(CHUNK_PATH_FORMAT, chunksDir, userId, chunkIndex);
			fileHelper.storeMultipartFile(multipartFile, fileName);
		} catch (IOException e) {
			throw new RestApplicationException("An Unexpected Error Occurred While Saving the File", HttpStatus.INTERNAL_SERVER_ERROR, e);
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
