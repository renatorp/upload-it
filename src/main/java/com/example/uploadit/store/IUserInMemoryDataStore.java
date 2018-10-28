package com.example.uploadit.store;

import java.util.List;

import com.example.uploadit.entity.User;

public interface IUserInMemoryDataStore {

	List<User> findAllUsers();

	void deleteUserById(Integer id);

	User createUser(User user);
}
