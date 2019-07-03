package com.globant.ecommerce.user.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globant.ecommerce.user.model.User;
import com.globant.ecommerce.user.service.UserServiceImpl;

@Service
public class UserFacadeImpl implements UserFacade {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Override
	public User register(User user) {
		return userService.register(user);
	}

	@Override
	public User login(String email, String password) {
		return userService.login(email, password);
	}

	@Override
	public User update(User user) {
		return userService.update(user);
	}

	@Override
	public boolean logout(User user) {
		return false;
	}

	@Override
	public User findByEmail(String email) {
		return userService.findByEmail(email);
	}

	@Override
	public User findById(int id) {
		return userService.FindById(id);
	}

	@Override
	public User checkUserLoginStatus(String authToken) {
		return userService.checkUserLoginStatus(authToken);
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userService.findAllUsers();
	}

	@Override
	public boolean authenticate(String authToken) throws Exception {
		return userService.authenticate(authToken);
	}

}
