package com.example.uploadit.component.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.uploadit.component.IErrorHandler;
import com.example.uploadit.exception.RestApplicationException;
import com.example.uploadit.vo.ErrorResponseVO;

@Component
public class ErrorHandler implements IErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
	
	@Override
	public ResponseEntity<Object> handleError(Exception e) {

		logger.error("ERROR", e);
		
		if (e instanceof RestApplicationException) {
			return handleBusinessError((RestApplicationException) e);
		}
		return handleGenericError(e);
	}

	private ResponseEntity<Object> handleBusinessError(RestApplicationException e) {
		return createErrorResponse(e.getMessage(), e.getHttpStatus());
	}

	private ResponseEntity<Object> createErrorResponse(String message, HttpStatus httpStatus) {
		return ResponseEntity.status(httpStatus).body(new ErrorResponseVO(message));
	}

	private ResponseEntity<Object> handleGenericError(Exception e) {
		return createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
