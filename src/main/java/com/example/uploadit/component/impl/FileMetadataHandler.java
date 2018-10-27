package com.example.uploadit.component.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.example.uploadit.component.IFileMetadataHandler;
import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.enums.UploadStatusEnum;
import com.example.uploadit.vo.FileRequestBody;

@Component
public class FileMetadataHandler implements IFileMetadataHandler{

	@Override
	public FileMetadata createMetadata(FileRequestBody requestBody, String userId) {
		FileMetadata fileMetadata = new FileMetadata();
		
		String id = Objects.nonNull(requestBody.getDzuuid()) ? requestBody.getDzuuid() : UUID.randomUUID().toString();
		
		fileMetadata.setId(id);
		fileMetadata.setFileName(requestBody.getFile().getOriginalFilename());
		fileMetadata.setUserId(userId);
		fileMetadata.setStatus(UploadStatusEnum.NOT_STARTED);
		fileMetadata.setTotalChunks(requestBody.getDztotalchunkcount());
		fileMetadata.setProcessedChunks(new HashSet<Integer>());
		
		return fileMetadata;
	}

	@Override
	public boolean isNew(FileMetadata metadata) {
		return UploadStatusEnum.NOT_STARTED.equals(metadata.getStatus());
	}

	@Override
	public void markAsProcessStarted(FileMetadata metadata) {
		metadata.setStatus(UploadStatusEnum.IN_PROGRESS);
		metadata.setDateTimeStartProcess(LocalDateTime.now());
	}

	@Override
	public boolean isChunked(FileMetadata metadata) {
		return metadata.getTotalChunks() != null;
	}

	@Override
	public void markChunkAsProcessed(FileMetadata metadata, Integer chunkIndex) {
		metadata.getProcessedChunks().add(chunkIndex);
	}

	@Override
	public void markAsProcessConcluded(FileMetadata metadata) {
		metadata.setStatus(UploadStatusEnum.CONCLUDED);
		metadata.setDateTimeEndProcess(LocalDateTime.now());
	}

}
