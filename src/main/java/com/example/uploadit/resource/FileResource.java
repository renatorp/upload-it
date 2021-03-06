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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.uploadit.component.IErrorHandler;
import com.example.uploadit.entity.User;
import com.example.uploadit.exception.RestApplicationException;
import com.example.uploadit.service.IFileService;
import com.example.uploadit.service.IUserService;
import com.example.uploadit.vo.FileRequestBody;
import com.example.uploadit.vo.FileResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("files")
@Api(tags = "Arquivos")
public class FileResource {

	private static final Logger logger = LoggerFactory.getLogger(FileResource.class);
	
	@Autowired
	private IFileService fileService;

	@Autowired
	private IErrorHandler errorHandler;

	@Autowired
	private IUserService userService;
	
	@ApiOperation(value = "Efetua upload de arquivo, esteja ele dividido em blocos ou não. "
			+ "Ao lidar com upload de múltiplos blocos, "
			+ "esta operação deve ser invocada para todos. "
			+ "A conclusão do processo será feita mediante chamada das operações de "
			+ "concluir com sucesso ou concluir com falha.",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiResponses({
			@ApiResponse(code = 201, message = "Upload de arquivo ou bloco de arquivo efetuado com êxito."),
			@ApiResponse(code = 500, message = "Erro inesperado no servidor."),
			@ApiResponse(code = 401, message = "Usuário enviado não foi encontrado (na vida real seria por token).")
	})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> handleFileUpload(@ApiParam("Arquivo a ser enviado") @RequestPart("file") MultipartFile file, @ApiParam("Identificador do usuário") @RequestParam("user") Integer userId, FileRequestBody requestBody) {
		try {
			User user = validateUser(userId);
			requestBody.setFile(file);
			fileService.uploadFile(requestBody, user.getId());
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@ApiOperation("Operação a ser realizada após operações de upload indicando "
			+ "que houve sucesso no processo. Caso o upload tenha sido feito em blocos, "
			+ "nesse momento eles são mesclados em um arquivo único arquivo para "
			+ "uso posterior.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Conclusão efetuada com sucesso."),
		@ApiResponse(code = 204, message = "Processo já concluído, nenhum alteração realizada."),
		@ApiResponse(code = 404, message = "Arquivo informado não encontrado."),
		@ApiResponse(code = 500, message = "Erro inesperado no servidor."),
		@ApiResponse(code = 401, message = "Usuário enviado não foi encontrado (na vida real seria por token).")
	})
	@PatchMapping("/{fileName}/success")
	public ResponseEntity<Object> handleUploadSuccess(@ApiParam("Nome do arquivo") @PathVariable("fileName") String fileName, @ApiParam("Identificador do usuário") @RequestParam("user") Integer userId) {
		try {
			
			validateUser(userId);
			
			fileService.cleanRemainingChunkFiles(userId);
			
			if (!fileService.isUploadInProgress(fileName, userId)) {
				return ResponseEntity.noContent().build();
			}
			fileService.concludeUploadWithSuccess(fileName, userId);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@ApiOperation("Operação a ser realizada após operações de upload indicando "
			+ "que houve falha no processo.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Conclusão efetuada com sucesso."),
		@ApiResponse(code = 404, message = "Arquivo informado não encontrado."),
		@ApiResponse(code = 500, message = "Erro inesperado no servidor."),
		@ApiResponse(code = 401, message = "Usuário enviado não foi encontrado (na vida real seria por token).")
	})
	@PatchMapping("{fileName}/failure")
	public ResponseEntity<Object> handleUploadFailure(@ApiParam("Nome do arquivo") @PathVariable("fileName") String fileName, @ApiParam("Identificador do usuário") @RequestParam("user") Integer userId) {
		try {
			validateUser(userId);
			
			fileService.cleanRemainingChunkFiles(userId);
			
			fileService.concludeUploadWithFailure(fileName, userId);
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@ApiOperation(value = "Lista informações sobre todos os arquivos que foram enviados.", response = List.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Dados retornados com sucesso."),
		@ApiResponse(code = 500, message = "Erro inesperado no servidor.")
	})
	@GetMapping
	public ResponseEntity<Object> listAllFiles() {
		try {
			List<FileResponseVO> files = fileService.listAllFiles();
			return ResponseEntity.ok(files);
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@ApiOperation("Efetua operação de download de arquivo.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Requisição de arquivo efetuada com sucesso."),
		@ApiResponse(code = 404, message = "Arquivo não encontrado."),
	})
	@GetMapping("{fileId}")
	public ResponseEntity<Object> downloadFile(@ApiParam("Identificador do arquivo") @PathVariable("fileId") String fileId, ServletRequest request) {
		
		try {
			Resource resource = fileService.retrieveFileAsResource(fileId);
			
	        String contentType = tryToDetermineContentType(request, resource);
	
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
        
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}

	private String tryToDetermineContentType(ServletRequest request, Resource resource) {
		String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        
        if (contentType != null) {
        	return contentType;
        }
        
        return "application/octet-stream";
	}
	
	private User validateUser(Integer userId) {
		User user = userService.getUser(userId);
		if (user == null) {
			throw new RestApplicationException("Usuário não foi encontrado!", HttpStatus.UNAUTHORIZED);
		}
		return user;
	}
	
}
