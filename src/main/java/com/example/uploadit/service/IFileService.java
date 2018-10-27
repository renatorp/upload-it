package com.example.uploadit.service;

import com.example.uploadit.vo.FileRequestBody;

public interface IFileService {

	void uploadFile(FileRequestBody requestBody, String userId);

}
