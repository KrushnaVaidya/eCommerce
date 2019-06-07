package com.globant.ecommerce.user.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.globant.ecommerce.user.model.User;
@Service
public interface UserDAO extends CrudRepository<User, Integer> {
	public User findByEmailAndPassword(String email,String password);
	public User findByEmail(String email);
	public User findById(int id);
	public User findByAuthToken(String authToken);
}
