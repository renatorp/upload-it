package com.example.uploadit.service;

import java.util.List;

import org.springframework.core.io.Resource;

import com.example.uploadit.vo.FileRequestBody;
import com.example.uploadit.vo.FileResponseVO;

public interface IFileService {

	void uploadFile(FileRequestBody requestBody, Integer userId);

	void concludeUploadWithSuccess(String fileName, Integer userId);

	boolean isUploadInProgress(String fileName, Integer userId);

	void concludeUploadWithFailure(String fileName, Integer userId);

	List<FileResponseVO> listAllFiles();

	Resource retrieveFileAsResource(String fileId);

	void cleanRemainingChunkFiles(Integer userId);

}
