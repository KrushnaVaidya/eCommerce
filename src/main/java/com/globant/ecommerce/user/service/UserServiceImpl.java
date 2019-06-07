package com.globant.ecommerce.user.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globant.ecommerce.user.DAO.UserDAO;
import com.globant.ecommerce.user.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;
	
	@Override
	public User register(User user) {
		return userDAO.save(user);
		
	}

	@Override
	public User login(String email, String password) {
		User user=userDAO.findByEmailAndPassword(email, password);
		String authToken=null;
		if(user!=null) {
	        int lowerLimit = 97; 
	        int upperLimit = 122; 
	        Random random = new Random(); 
	        StringBuffer r = new StringBuffer(20); 
	  
	        for (int i = 0; i < 20; i++) { 
	            int nextRandomChar = lowerLimit+(int)(random.nextFloat() * (upperLimit - lowerLimit + 1)); 
	            r.append((char)nextRandomChar); 
	        }
	        authToken=r.toString();
	        System.out.println(authToken);
	        user.setAuthToken(authToken);
	        user=userDAO.save(user);
	        return user;
		}
		return null;
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return userDAO.save(user);
	}

	@Override
	public boolean logout(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}

	@Override
	public User FindById(int id) {
		// TODO Auto-generated method stub
		return userDAO.findById(id);
	}

	@Override
	public User checkUserLoginStatus(String authToken) {
		User user=userDAO.findByAuthToken(authToken);
		if(user!=null) {
			return user;
		}
		return null;
	}

	@Override
	public List<User> findAllUsers() {	
		return (List<User>) userDAO.findAll();
	}

}
