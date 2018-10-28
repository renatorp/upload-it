package com.example.uploadit.resource;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.uploadit.component.IErrorHandler;
import com.example.uploadit.service.IFileService;
import com.example.uploadit.vo.FileRequestBody;
import com.example.uploadit.vo.FileResponseVO;

@RestController
@RequestMapping("files")
public class FileResource {

	private static final Logger logger = LoggerFactory.getLogger(FileResource.class);
	
	@Autowired
	private IFileService fileService;

	@Autowired
	private IErrorHandler errorHandler;

	@CrossOrigin /// TODO: REMOVER
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> handleFileUpload(@RequestParam("user") String userId, FileRequestBody requestBody) {

		try {
			fileService.uploadFile(requestBody, userId);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@CrossOrigin /// TODO: REMOVER
	@PatchMapping("/{fileName}/success")
	public ResponseEntity<Object> handleUploadSuccess(@PathVariable("fileName") String fileName, @RequestParam("user") String userId) {
		try {
			if (!fileService.isUploadInProgress(fileName, userId)) {
				return ResponseEntity.noContent().build();
			}
			fileService.concludeUpload(fileName, userId);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}

	@CrossOrigin /// TODO: REMOVER
	@PatchMapping("{fileName}/failure")
	public ResponseEntity<Object> handleUploadFailure(@PathVariable("fileName") String fileName, @RequestParam("user") String userId) {
		try {
			fileService.concludeUploadWithFailure(fileName, userId);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@CrossOrigin /// TODO: REMOVER
	@GetMapping
	public ResponseEntity<Object> listAllFiles() {
		try {
			List<FileResponseVO> files = fileService.listAllFiles();
			return ResponseEntity.ok(files);
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@CrossOrigin /// TODO: REMOVER
	@GetMapping("{fileId}")
	public ResponseEntity<Object> downloadFile(@PathVariable("fileId") String fileId, ServletRequest request) {
		
		Resource resource = fileService.retrieveFileAsResource(fileId);
		
        String contentType = tryToDetermineContentType(request, resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}

	private String tryToDetermineContentType(ServletRequest request, Resource resource) {
        try {
            return request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        
        return "application/octet-stream";
	}
}
