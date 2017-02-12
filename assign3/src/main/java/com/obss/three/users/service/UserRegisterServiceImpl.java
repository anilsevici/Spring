package com.obss.three.users.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.obss.three.users.dao.UserDao;
import com.obss.three.users.model.User;
import com.obss.three.users.model.UserRole;

@Service("userRegisterService")
@Transactional
public class UserRegisterServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public void saveUser(User user) {
		Set<UserRole> roles = new HashSet<>();
		roles.add(new UserRole(user, "ROLE_USER"));

		user.setUserRole(roles);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);

		userDao.saveUser(user);

	}

	@Override
	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}

	@Override
	public boolean isUserUsernameUnique(String username) {
		User user = userDao.findByUserName(username);

		return user == null;
	}

	@Override
	public boolean isUserEmailUnique(String email) {
		User user = userDao.findByEmail(email);

		return user == null;
	}

}
