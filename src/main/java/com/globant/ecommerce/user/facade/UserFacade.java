package com.globant.ecommerce.user.facade;

import java.util.List;

import com.globant.ecommerce.user.model.User;

public interface UserFacade {
	public User register(User user);
	public User login(String email,String password);
	public User update(User user);
	public boolean logout(User user);
	public User findByEmail(String email);
	public User findById(int id);
	public User checkUserLoginStatus(String authToken);
	public List<User> findAllUsers();
	public boolean authenticate(String authToken) throws Exception;
}
