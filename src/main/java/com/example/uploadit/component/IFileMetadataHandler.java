package com.example.uploadit.component;

import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.vo.FileRequestBody;

public interface IFileMetadataHandler {

	FileMetadata createMetadata(FileRequestBody requestBody, Integer userId);

	boolean isNew(FileMetadata metadata);

	void markAsProcessStarted(FileMetadata metadata);

	boolean isChunked(FileMetadata metadata);

	void markChunkAsProcessed(FileMetadata metadata, Integer chunkIndex);

	void markAsProcessConcluded(FileMetadata metadata);

	boolean isInProgress(FileMetadata metadata);

	void markAsProcessFailed(FileMetadata fileMetadata);

	boolean isProcessFinished(FileMetadata metadata);

	String generateDownloadUri(FileMetadata metadata);

	Long calculateProcessDurationInMillis(FileMetadata metadata);

}
