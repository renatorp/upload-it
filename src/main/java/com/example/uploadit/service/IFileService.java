package com.example.uploadit.service;

import java.util.List;

import com.example.uploadit.vo.FileRequestBody;
import com.example.uploadit.vo.FileResponseVO;

public interface IFileService {

	void uploadFile(FileRequestBody requestBody, String userId);

	void concludeUpload(String fileName, String userId);

	boolean isUploadInProgress(String fileName, String userId);

	void concludeUploadWithFailure(String fileName, String userId);

	List<FileResponseVO> listAllFiles();

}
