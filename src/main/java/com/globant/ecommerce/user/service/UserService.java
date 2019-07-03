package com.globant.ecommerce.user.service;

import java.util.List;

import com.globant.ecommerce.user.model.User;
/**
 * 
 * @author krushna.vaidya
 *
 */
public interface UserService {
	
	/**
	 * This method register each new user. 
	 * @param user
	 * @return
	 */
	public User register(User user);
	/**
	 * This method check Login status of user.
	 * @param email
	 * @param password
	 * @return
	 */
	public User login(String email,String password);
	/**
	 * This Method update user Details.
	 * @param user
	 * @return
	 */
	public User update(User user);
	/**
	 * This method Logout the user.
	 * @param user
	 * @return
	 */
	public boolean logout(User user);
	/**
	 * This method return return user based on email.
	 * @param email
	 * @return
	 */
	public User findByEmail(String email);
	/**
	 * This method return user by Id.
	 * @param id
	 * @return
	 */
	public User FindById(int id);
	/**
	 * This method return User Login Status of user .
	 * @param authToken
	 * @return
	 */
	public User checkUserLoginStatus(String authToken);
	/**
	 * This method return all user list.
	 * @return List<User>
	 */
	public List<User> findAllUsers();
	/**
	 * This method authenticate user.
	 * @param authToken
	 * @return
	 * @throws Exception 
	 */
	public boolean authenticate(String authToken) throws Exception;
}
