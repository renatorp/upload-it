package com.example.uploadit.enums;

public enum UploadStatusEnum {
	NOT_STARTED("Não Iniciado"), IN_PROGRESS("Em Progresso"), CONCLUDED("Concluído"), FAILED("Falha");

	private String description;

	private UploadStatusEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
