package com.example.uploadit.entity;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.uploadit.enums.UploadStatusEnum;

public class FileMetadata {

	private String id;
	private String fileName;
	private Integer userId;
	private Integer totalChunks;
	private Set<Integer> processedChunks;
	private UploadStatusEnum status;
	private LocalDateTime dateTimeStartProcess;
	private LocalDateTime dateTimeEndProcess;
	
	/**
	 * Indica que, devido a uma falha no processo de upload do arquivo,
	 * alguns blocos (chunks) não tenha sido removidos do servidor. 
	 */
	private boolean dirty;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTotalChunks() {
		return totalChunks;
	}

	public void setTotalChunks(Integer totalChunks) {
		this.totalChunks = totalChunks;
	}

	public Set<Integer> getProcessedChunks() {
		return processedChunks;
	}

	public void setProcessedChunks(Set<Integer> processedChunks) {
		this.processedChunks = processedChunks;
	}

	public UploadStatusEnum getStatus() {
		return status;
	}

	public void setStatus(UploadStatusEnum status) {
		this.status = status;
	}

	public LocalDateTime getDateTimeStartProcess() {
		return dateTimeStartProcess;
	}

	public void setDateTimeStartProcess(LocalDateTime dateTimeStartProcess) {
		this.dateTimeStartProcess = dateTimeStartProcess;
	}

	public LocalDateTime getDateTimeEndProcess() {
		return dateTimeEndProcess;
	}

	public void setDateTimeEndProcess(LocalDateTime dateTimeEndProcess) {
		this.dateTimeEndProcess = dateTimeEndProcess;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
