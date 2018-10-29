package com.example.uploadit.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uploadit.component.IErrorHandler;
import com.example.uploadit.entity.User;
import com.example.uploadit.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("users")
@Api(tags = "Usuários")
public class UserResource {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IErrorHandler errorHandler;
	
	@CrossOrigin
	@ApiOperation(value = "Cria um novo usuário.", response = User.class)
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário criado com sucesso."),
		@ApiResponse(code = 200, message = "O nome ou a senha não foram informados.")
	})
	@PutMapping
	public ResponseEntity<Object> createUser(@RequestBody User requestBody) {
		try {
			User user = userService.createUser(requestBody);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
	
	@CrossOrigin
	@ApiOperation("Deleta um usuário cadastrado. O usuário id=1 (admin) não pode ser removido.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário removido com sucesso."),
	})
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteUser(@ApiParam("Identificador do usuário") @PathVariable("id") Integer id) {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}
	
	@CrossOrigin
	@ApiOperation(value = "Busca todos os usuários cadastrados.", response = List.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Busca efetuada com sucesso."),
	})
	@GetMapping
	public ResponseEntity<Object> findUsers() {
		List<User> users = userService.findUsers();
		return ResponseEntity.ok(users);
	}
	
	@CrossOrigin
	@ApiOperation(value = "Valida se um usuário possui o nome e a senha "
			+ "informados. Se o usuário for encontrado, é retornado.", response = User.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "O nome e a senha são válidos."),
		@ApiResponse(code = 401, message = "O nome e a senha não são válidos."),
		@ApiResponse(code = 400, message = "O nome ou a senha não foram informados.")
	})
	@PostMapping("login")
	public ResponseEntity<Object> validateUser(@RequestBody User requestBody) {
		try {
			User user = userService.validateUser(requestBody);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return errorHandler.handleError(e);
		}
	}
}
