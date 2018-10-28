package com.example.uploadit.store.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.uploadit.entity.User;
import com.example.uploadit.store.IUserInMemoryDataStore;

@Component
public class UserInMemoryDataStore implements IUserInMemoryDataStore {

	private static final List<User> users = new ArrayList<>();
	
	@Override
	public List<User> findAllUsers() {
		return users;
	}

	@Override
	public void deleteUserById(Integer id) {
		users.removeIf(u -> u.getId().equals(id));
	}

	@Override
	public User createUser(User user) {
		user.setId(generateId());
		users.add(user);
			
		return user;
	}

	private Integer generateId() {
		Optional<Integer> max = users.stream().map(User::getId).max(Integer::compare);
		if (max.isPresent()) {
			return max.get() + 1;
		} 
		return 1;
	}

}
