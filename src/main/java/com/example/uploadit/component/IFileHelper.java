package com.example.uploadit.component;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileHelper {

	void storeMultipartFile(MultipartFile multipartFile, String fileName) throws IOException;

	void mergeFiles(List<String> sourceFiles, String targetFile) throws IOException;

	void deleteDir(String sourceFolder);

	Resource loadFileAsResource(String filePath);

}
