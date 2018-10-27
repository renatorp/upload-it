package com.example.uploadit.component.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.uploadit.component.IFileHelper;

@Component
public class FileHelper implements IFileHelper {

	@Override
	public void storeMultipartFile(MultipartFile multipartFile, String fileName) throws IOException {
		InputStream fileStream = multipartFile.getInputStream();
		File targetFile = new File(fileName);
		FileUtils.copyInputStreamToFile(fileStream, targetFile);
	}

	@Override
	public void mergeFiles(List<String> sourceFilesNames, String targetFileName) throws IOException {
		File targetFile = new File(targetFileName);
		for (String sourceFileName : sourceFilesNames) {
			File sourceFile = new File(sourceFileName);
			FileUtils.writeByteArrayToFile(targetFile, IOUtils.toByteArray(new FileInputStream(sourceFile)), true);
		}
	}

}
