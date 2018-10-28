package com.example.uploadit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.uploadit.entity.User;
import com.example.uploadit.exception.RestApplicationException;
import com.example.uploadit.service.IUserService;
import com.example.uploadit.store.IUserInMemoryDataStore;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserInMemoryDataStore dataStore;
	
	@Override
	public User createUser(User user) {
		return dataStore.createUser(user);
	}

	@Override
	public void deleteUser(Integer id) {
		if (id != 1) {
			dataStore.deleteUserById(id);
		}
	}

	@Override
	public List<User> findUsers() {
		return dataStore.findAllUsers();
	}

	@Override
	public User validateUser(User user) {
		
		if (StringUtils.isEmpty(user.getName())) {
			throw new RestApplicationException("User name is mandatory!", HttpStatus.BAD_REQUEST);
		}
		
		if (StringUtils.isEmpty(user.getPassword())) {
			throw new RestApplicationException("User password is mandatory", HttpStatus.BAD_REQUEST);
		}
		
		Optional<User> opt = dataStore.findUserByNameAndPassword(user);
		
		if (!opt.isPresent()) {
			throw new RestApplicationException("Authentication Failed!", HttpStatus.UNAUTHORIZED);
		}
		
		return opt.get();
	}
}
