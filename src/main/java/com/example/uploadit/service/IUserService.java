package com.example.uploadit.service;

import java.util.List;

import com.example.uploadit.entity.User;

public interface IUserService {

	User createUser(User requestBody);

	void deleteUser(Integer id);

	List<User> findUsers();

	User validateUser(User user);

}
