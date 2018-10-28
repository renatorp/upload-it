package com.example.uploadit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uploadit.entity.User;
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

}
