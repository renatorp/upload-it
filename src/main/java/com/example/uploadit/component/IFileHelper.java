package com.example.uploadit.component;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IFileHelper {

	void storeMultipartFile(MultipartFile multipartFile, String fileName) throws IOException;

}
