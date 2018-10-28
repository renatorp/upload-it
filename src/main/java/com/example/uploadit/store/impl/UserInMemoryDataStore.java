package com.example.uploadit.store.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.uploadit.entity.User;
import com.example.uploadit.store.IUserInMemoryDataStore;

@Component
public class UserInMemoryDataStore implements IUserInMemoryDataStore {

	private static final List<User> userStore = new ArrayList<>();
	
	public UserInMemoryDataStore() {
		createUserAdmin();
	}

	private void createUserAdmin() {
		User user = new User();
		user.setId(1);
		user.setName("admin");
		user.setPassword("admin");
		createUser(user);
	}
	
	@Override
	public List<User> findAllUsers() {
		return userStore;
	}

	@Override
	public void deleteUserById(Integer id) {
		userStore.removeIf(u -> u.getId().equals(id));
	}

	@Override
	public User createUser(User user) {
		user.setId(generateId());
		userStore.add(user);
			
		return user;
	}

	private Integer generateId() {
		Optional<Integer> max = userStore.stream().map(User::getId).max(Integer::compare);
		if (max.isPresent()) {
			return max.get() + 1;
		} 
		return 1;
	}

}
