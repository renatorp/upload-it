package com.example.uploadit.enums;

public enum UploadStatusEnum {
	NOT_STARTED(0), IN_PROGRESS(1), CONCLUDED(2), FAILED(3);

	private Integer key;

	private UploadStatusEnum(Integer key) {
		this.key = key;
	}

	public Integer getKey() {
		return key;
	}

}
