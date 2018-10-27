package com.example.uploadit.component;

import org.springframework.http.ResponseEntity;

public interface IErrorHandler {

	ResponseEntity<Object> handleError(Exception e);

}
