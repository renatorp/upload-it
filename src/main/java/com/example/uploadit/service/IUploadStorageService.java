package com.example.uploadit.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadStorageService {

	void storeChunk(MultipartFile file, Integer chunkIndex, Integer userId, String fileId);

	void storeFile(MultipartFile file, Integer userId);

	void mergeFileChunks(String fileName, Integer totalChunks, Integer userId, String fileId) throws IOException;

	void deleteFileChunks(Integer userId, String fileName);

	String retrieveFilePath(Integer userId, String fileName);

}
