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
		// TODO Auto-generated method stub
		return userService.login(email, password);
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return userService.update(user);
	}

	@Override
	public boolean logout(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userService.findByEmail(email);
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return userService.FindById(id);
	}

	@Override
	public User checkUserLoginStatus(String authToken) {
		// TODO Auto-generated method stub
		return userService.checkUserLoginStatus(authToken);
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userService.findAllUsers();
	}

}
