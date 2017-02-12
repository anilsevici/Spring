package com.obss.three.users.service;

import java.util.List;

import com.obss.three.users.model.User;

public interface UserService {

	User findByUserName(String username);

	void saveUser(User user);

	List<User> findAllUsers();

	boolean isUserUsernameUnique(String username);

	boolean isUserEmailUnique(String email);

}
