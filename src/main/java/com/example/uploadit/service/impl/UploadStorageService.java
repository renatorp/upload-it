package com.example.uploadit.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.uploadit.component.IFileHelper;
import com.example.uploadit.exception.RestApplicationException;
import com.example.uploadit.service.IUploadStorageService;

@Service
public class UploadStorageService implements IUploadStorageService {

	private static final String CHUNKS_DIR_PATH_FORMAT = "%s/%s/%s/";
	private static final String CHUNKS_FILE_EXTENSION = ".tmp";
	private static final String FILE_PATH_FORMAT = "%s/%s/%s";
	
	@Value("${upload-it.chunks.dir}")
	private String chunksDir;

	@Value("${upload-it.files.dir}")
	private String filesDir;

	@Autowired
	private IFileHelper fileHelper;
	
	@Override
	public void storeChunk(MultipartFile multipartFile, Integer chunkIndex, Integer userId, String fileId) {

		try {
			String filePath = retrieveChunkPath(chunkIndex, userId, fileId);
			fileHelper.storeMultipartFile(multipartFile, filePath);
		} catch (IOException e) {
			throw new RestApplicationException("Ocorreu um erro inesperado ao salvar o arquivo.",
					HttpStatus.INTERNAL_SERVER_ERROR, e);
		}

	}

	@Override
	public void storeFile(MultipartFile multipartFile, Integer userId) {
		try {
			String filePath = retrieveFilePath(userId, multipartFile.getOriginalFilename());
			fileHelper.storeMultipartFile(multipartFile, filePath);
		} catch (IOException e) {
			throw new RestApplicationException("Ocorreu um erro inesperado ao salvar o arquivo",
					HttpStatus.INTERNAL_SERVER_ERROR, e);
		}

	}

	@Override
	public void mergeFileChunks(String fileName, Integer totalChunks, Integer userId, String fileId) throws IOException {

		String targetFile = retrieveFilePath(userId, fileName);

		List<String> sourceFiles = Stream.iterate(0, i -> ++i)
				.limit(totalChunks)
				.map(i -> retrieveChunkPath(i, userId, fileId))
				.collect(Collectors.toList());

		fileHelper.mergeFiles(sourceFiles, targetFile);

		deleteFileChunks(userId, fileId);
	}
	
	@Override
	public void deleteFileChunks(Integer userId, String fileId) {
		fileHelper.deleteDir(retrieveChunksDir(userId, fileId));
	}
	
	@Override
	public String retrieveFilePath(Integer userId, String fileName) {
		return String.format(FILE_PATH_FORMAT, filesDir, userId, fileName);
	}
	
	private String retrieveChunkPath(Integer chunkIndex, Integer userId, String fileId) {
		return retrieveChunksDir(userId, fileId) + chunkIndex + CHUNKS_FILE_EXTENSION;
	}

	private String retrieveChunksDir(Integer userId, String fileId) {
		return String.format(CHUNKS_DIR_PATH_FORMAT, chunksDir, userId, fileId);
	}
}
