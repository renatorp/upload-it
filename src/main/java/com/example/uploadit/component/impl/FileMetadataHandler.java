package com.example.uploadit.component.impl;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.uploadit.component.IFileMetadataHandler;
import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.enums.UploadStatusEnum;
import com.example.uploadit.vo.FileRequestBody;

@Component
public class FileMetadataHandler implements IFileMetadataHandler {

	@Override
	public FileMetadata createMetadata(FileRequestBody requestBody, String userId) {
		FileMetadata fileMetadata = new FileMetadata();

		String id = Objects.nonNull(requestBody.getDzuuid()) ? requestBody.getDzuuid() : UUID.randomUUID().toString();
		Integer numChunks = Objects.nonNull(requestBody.getDztotalchunkcount()) ? requestBody.getDztotalchunkcount()
				: 1;
		fileMetadata.setId(id);
		fileMetadata.setFileName(requestBody.getFile().getOriginalFilename());
		fileMetadata.setUserId(userId);
		fileMetadata.setStatus(UploadStatusEnum.NOT_STARTED);
		fileMetadata.setTotalChunks(numChunks);
		fileMetadata.setProcessedChunks(new HashSet<Integer>());

		return fileMetadata;
	}

	@Override
	public boolean isNew(FileMetadata metadata) {
		return UploadStatusEnum.NOT_STARTED.equals(metadata.getStatus());
	}

	@Override
	public boolean isInProgress(FileMetadata metadata) {
		return UploadStatusEnum.IN_PROGRESS.equals(metadata.getStatus());
	}

	@Override
	public void markAsProcessFailed(FileMetadata metadata) {
		metadata.setStatus(UploadStatusEnum.FAILED);
		metadata.setDateTimeEndProcess(LocalDateTime.now());
		metadata.setDirty(true);
	}

	@Override
	public void markAsProcessStarted(FileMetadata metadata) {
		metadata.setStatus(UploadStatusEnum.IN_PROGRESS);
		metadata.setDateTimeStartProcess(LocalDateTime.now());
	}

	@Override
	public boolean isChunked(FileMetadata metadata) {
		return metadata.getTotalChunks() > 1;
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

	@Override
	public boolean isProcessFinished(FileMetadata metadata) {
		return UploadStatusEnum.CONCLUDED.equals(metadata.getStatus());
	}
	
	@Override
	public Long calculateProcessDurationInMillis(FileMetadata metadata) {
		if (isProcessFinished(metadata)) {
			return Duration.between(metadata.getDateTimeStartProcess(), metadata.getDateTimeEndProcess()).toMillis();
		}
		return null;
	}
	
	@Override
	public String generateDownloadUri(FileMetadata metadata) {
		if (isProcessFinished(metadata)) {
			URI downloadLink = ServletUriComponentsBuilder.fromCurrentContextPath().path("files/{fileId}").buildAndExpand(metadata.getId()).toUri();
			return downloadLink.toString();
		}
		return null;
	}
}
