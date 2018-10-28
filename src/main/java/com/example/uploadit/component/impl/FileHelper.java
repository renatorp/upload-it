package com.example.uploadit.component.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

	@Override
	public void deleteDir(String dirPath) {
		File dir = new File(dirPath);
		String[]entries = dir.list();
		for(String s: entries){
		    new File(dir.getPath(),s).delete();
		}
		dir.delete();
	}
	
	@Override
	public Resource loadFileAsResource(String filePath){
		try {
			File file = new File(filePath);
			UrlResource resource = new UrlResource(file.toURI());
			if (!resource.exists()) {
				return null;
			}
			return resource;
		} catch (MalformedURLException e) {
			return null;
		}
		
	}

}
