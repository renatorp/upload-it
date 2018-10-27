package com.example.uploadit.vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileRequestBody implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private MultipartFile file;
	private String dzuuid;

	public String getDzuuid() {
		return dzuuid;
	}

	public void setDzuuid(String dzuuid) {
		this.dzuuid = dzuuid;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
