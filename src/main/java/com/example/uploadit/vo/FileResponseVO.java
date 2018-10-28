package com.example.uploadit.vo;

import java.io.Serializable;

public class FileResponseVO implements Serializable {

	private static final long serialVersionUID = -3366198496316925959L;

	private String id;
	private String userId;
	private String fileName;
	private String status;
	private Long processDuration;
	private Integer quantityOfBlocks;
	private String download;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getProcessDuration() {
		return processDuration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setProcessDuration(Long processDuration) {
		this.processDuration = processDuration;
	}

	public Integer getQuantityOfBlocks() {
		return quantityOfBlocks;
	}

	public void setQuantityOfBlocks(Integer quantityOfBlocks) {
		this.quantityOfBlocks = quantityOfBlocks;
	}

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

}
