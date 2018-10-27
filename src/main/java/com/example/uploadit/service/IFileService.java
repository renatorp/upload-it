package com.example.uploadit.service;

import com.example.uploadit.vo.FileRequestBody;

public interface IFileService {

	void uploadFileChunk(FileRequestBody requestBody, String userId);

}
