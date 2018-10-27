package com.example.uploadit.component;

import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.vo.FileRequestBody;

public interface IFileMetadataHandler {

	FileMetadata createMetadata(FileRequestBody requestBody, String userId);

	boolean isNew(FileMetadata metadata);

	void markAsProcessStarted(FileMetadata metadata);

	boolean isChunked(FileMetadata metadata);

	void markChunkAsProcessed(FileMetadata metadata, Integer chunkIndex);

	void markAsProcessConcluded(FileMetadata metadata);

}
