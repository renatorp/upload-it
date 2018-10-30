package com.example.uploadit.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.uploadit.component.IFileHelper;
import com.example.uploadit.component.IFileMetadataHandler;
import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.exception.RestApplicationException;
import com.example.uploadit.service.IFileService;
import com.example.uploadit.service.IUploadStorageService;
import com.example.uploadit.store.IFileInMemoryDataStore;
import com.example.uploadit.vo.FileRequestBody;
import com.example.uploadit.vo.FileResponseVO;

@Service
public class FileService implements IFileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private IFileInMemoryDataStore dataStore;

	@Autowired
	private IFileMetadataHandler fileMetadataHandler;

	@Autowired
	private IFileHelper fileHelper;
	
	@Autowired
	private IUploadStorageService uploadStorageService;

	/**
	 * Armazena chunks de arquivos que foram subidos, assim como seus metadados.
	 */
	@Override
	public void uploadFile(FileRequestBody requestBody, Integer userId) {

		FileMetadata metadata = getFileMetadata(requestBody, userId);

		if (fileMetadataHandler.isNew(metadata)) {
			fileMetadataHandler.markAsProcessStarted(metadata);
		}

		storeContent(requestBody, metadata);
	}

	/**
	 * Se o upload for de chunks, o armazena. Caso contrário, armazena o arquivo
	 * completo e finaliza o processo de upload.
	 */
	private void storeContent(FileRequestBody requestBody, FileMetadata metadata) {

		if (fileMetadataHandler.isChunked(metadata)) {
			uploadStorageService.storeChunk(requestBody.getFile(), requestBody.getDzchunkindex(), metadata.getUserId(), metadata.getId());
			fileMetadataHandler.markChunkAsProcessed(metadata, requestBody.getDzchunkindex());

		} else {
			uploadStorageService.storeFile(requestBody.getFile(), metadata.getUserId());
			fileMetadataHandler.markAsProcessConcluded(metadata);
		}

	}

	/**
	 * Obtém metadados de arquivo. Se o arquivo for novo, cria um registro e o
	 * armazena/retorna.
	 */
	private FileMetadata getFileMetadata(FileRequestBody requestBody, Integer userId) {

		Optional<FileMetadata> uploadedFile = dataStore.findMetadataFileById(requestBody.getDzuuid());

		if (uploadedFile.isPresent()) {
			return uploadedFile.get();
		}

		cleanExistingFileMetadata(requestBody.getFile().getOriginalFilename(), userId);

		FileMetadata metadata = fileMetadataHandler.createMetadata(requestBody, userId);
		dataStore.insertMetadataFile(metadata);
		return metadata;

	}

	private void cleanExistingFileMetadata(String fileName, Integer userId) {
		dataStore.deleteFileMetadataByUserIdAndFileName(fileName, userId);
	}

	@Override
	public boolean isUploadInProgress(String fileName, Integer userId) {
		FileMetadata fileMetadata = findFileMetadata(fileName, userId);
		return fileMetadataHandler.isInProgress(fileMetadata);
	}

	@Override
	public void concludeUploadWithSuccess(String fileName, Integer userId) {
		
		FileMetadata fileMetadata = findFileMetadata(fileName, userId);

		try {
			uploadStorageService.mergeFileChunks(fileMetadata.getFileName(), fileMetadata.getTotalChunks(), fileMetadata.getUserId(), fileMetadata.getId());
			fileMetadataHandler.markAsProcessConcluded(fileMetadata);
		} catch (IOException e) {
			fileMetadataHandler.markAsProcessFailed(fileMetadata);
			throw new RestApplicationException("Ocorre um erro inesperado ao mesclar blocos de arquivo",
					HttpStatus.INTERNAL_SERVER_ERROR, e);
		}

	}

	private FileMetadata findFileMetadata(String fileName, Integer userId) {
		Optional<FileMetadata> optional = dataStore.findMetadataFileByUserAndFileName(fileName, userId);

		if (!optional.isPresent()) {
			throw new RestApplicationException(String.format("Arquivo %s não encontrado!", fileName), HttpStatus.NOT_FOUND);
		}

		return optional.get();
	}

	@Override
	public void concludeUploadWithFailure(String fileName, Integer userId) {
		FileMetadata fileMetadata = findFileMetadata(fileName, userId);
		fileMetadataHandler.markAsProcessFailed(fileMetadata);
	}

	
	/**
	 * Tenta limpar temporários gerados para não acumular no servidor.
	 */
	@Override
	public void cleanRemainingChunkFiles(Integer userId) {
		try {
			dataStore.findDirtyFilesMetadataByUser(userId).forEach(m -> {
				uploadStorageService.deleteFileChunks(userId, m.getId());
				m.setDirty(false);
			});
		} catch (Exception e) {
			logger.info("Não foi possível limpar chunks!!", e);
		}
	}

	@Override
	public List<FileResponseVO> listAllFiles() {
		List<FileMetadata> allMetadata = dataStore.findAllFilesMetadata();
		
		return allMetadata.stream()
			.map(this::convertFileMetadataToResponseVO)
			.collect(Collectors.toList());
	}

	private FileResponseVO convertFileMetadataToResponseVO(FileMetadata metadata) {
		
		FileResponseVO vo = new FileResponseVO();

		vo.setId(metadata.getId());
		vo.setUserId(metadata.getUserId());
		vo.setFileName(metadata.getFileName());
		vo.setStatus(metadata.getStatus().getDescription());
		vo.setProcessDuration(fileMetadataHandler.calculateProcessDurationInMillis(metadata));
		vo.setDownload(fileMetadataHandler.generateDownloadUri(metadata));
		vo.setQuantityOfBlocks(metadata.getTotalChunks());
		
		return vo;
	}

	@Override
	public Resource retrieveFileAsResource(String fileId) {
		Optional<FileMetadata> uploadedFile = dataStore.findMetadataFileById(fileId);
		
		if (!uploadedFile.isPresent()) {
			throw new RestApplicationException("Arquivo não encontrado", HttpStatus.NOT_FOUND);
		}

		FileMetadata fileMetadata = uploadedFile.get();

		String filePath = uploadStorageService.retrieveFilePath(fileMetadata.getUserId(), fileMetadata.getFileName());
		Resource resource = fileHelper.loadFileAsResource(filePath);
		
		if (resource == null) {
			throw new RestApplicationException("Arquivo não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return resource;
		
	}

}