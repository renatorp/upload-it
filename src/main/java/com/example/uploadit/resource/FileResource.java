package com.example.uploadit.resource;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.uploadit.component.IErrorHandler;
import com.example.uploadit.service.IFileService;
import com.example.uploadit.vo.FileRequestBody;

@RestController
@RequestMapping("files")
public class FileResource {

	@Autowired
	private IFileService fileService;

	@Autowired
	private IErrorHandler errorHandler;

	@CrossOrigin /// REMOVER
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> handleFileUpload(@RequestParam("user") String userId, FileRequestBody requestBody) {

		try {
			fileService.uploadFile(requestBody, userId);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@PatchMapping("{fileName}/success")
	public ResponseEntity<Object> handleUploadSuccess(@PathParam("fileName") String fileName, @RequestParam("user") String userId) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok().build();
	}

	@PatchMapping("{fileName}/failure")
	public ResponseEntity<Object> handleUploadFailure(@PathParam("fileName") String fileName, @RequestParam("user") String userId) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok().build();
	}
}
