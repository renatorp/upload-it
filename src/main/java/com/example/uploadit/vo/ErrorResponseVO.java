package com.example.uploadit.vo;

import java.io.Serializable;

public class ErrorResponseVO implements Serializable {

	private static final long serialVersionUID = 2333325557718245579L;
	private String message;

	public ErrorResponseVO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
