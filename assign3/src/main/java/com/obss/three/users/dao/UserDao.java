package com.obss.three.users.dao;

import java.util.List;

import com.obss.three.users.model.User;

public interface UserDao {

	User findByUserName(String username);

	User findByEmail(String email);

	void saveUser(User user);

	List<User> findAllUsers();

}
