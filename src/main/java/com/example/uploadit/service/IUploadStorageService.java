package com.example.uploadit.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadStorageService {

	void storeChunk(MultipartFile file, Integer chunkIndex, String userId, String fileId);

	void storeFile(MultipartFile file, String userId);

	void mergeFileChunks(String fileName, Integer totalChunks, String userId, String fileId) throws IOException;

	void deleteFileChunks(String userId, String fileName);

	String retrieveFilePath(String userId, String fileName);

}
