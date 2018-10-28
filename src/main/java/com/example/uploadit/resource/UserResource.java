package com.example.uploadit.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uploadit.entity.User;
import com.example.uploadit.service.IUserService;

@RestController
@RequestMapping("users")
public class UserResource {

	@Autowired
	private IUserService userService;
	
	@CrossOrigin
	@PutMapping
	public ResponseEntity<Object> createUser(@RequestBody User requestBody) {
		User user = userService.createUser(requestBody);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@CrossOrigin
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Integer id) {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<Object> findUsers() {
		List<User> users = userService.findUsers();
		return ResponseEntity.ok(users);
	}
}
