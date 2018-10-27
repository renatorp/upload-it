package com.example.uploadit.exception;

import org.springframework.http.HttpStatus;

public class RestApplicationException extends RuntimeException {

	private static final long serialVersionUID = 9071837857160970033L;

	private HttpStatus httpStatus;

	public RestApplicationException(String message, HttpStatus httpStatus, Throwable e) {
		super(message, e);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
