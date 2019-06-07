package com.globant.ecommerce.user.service;

import java.util.List;

import com.globant.ecommerce.user.model.User;

public interface UserService {
	public User register(User user);
	public User login(String email,String password);
	public User update(User user);
	public boolean logout(User user);
	public User findByEmail(String email);
	public User FindById(int id);
	public User checkUserLoginStatus(String authToken);
	public List<User> findAllUsers();
}
