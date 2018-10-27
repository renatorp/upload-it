package com.example.uploadit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RestApplicationException extends RuntimeException {

	private static final long serialVersionUID = 9071837857160970033L;

	public RestApplicationException(String message, Throwable e) {
		super(message, e);
	}

}
