package com.example.uploadit.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.uploadit.service.IFileService;
import com.example.uploadit.vo.FileRequestBody;

@RestController
@RequestMapping("files")
public class FileResource {

	@Autowired
	private IFileService fileService;

	@CrossOrigin
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> handleFileChunkUpload(@RequestParam("user") String userId, FileRequestBody requestBody) {

		fileService.uploadFileChunk(requestBody, userId);
		
		return ResponseEntity.ok().build();
    }
	
}
