package com.example.uploadit.service.impl;

import org.springframework.stereotype.Service;

import com.example.uploadit.service.IFileService;
import com.example.uploadit.vo.FileRequestBody;

@Service
public class FileService implements IFileService {

	@Override
	public void uploadFileChunk(FileRequestBody requestBody, String userId) {
		System.out.println("teste");
	}

}
