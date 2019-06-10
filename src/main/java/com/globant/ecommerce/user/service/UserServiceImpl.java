package com.globant.ecommerce.user.service;
import java.util.List;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.globant.ecommerce.user.DAO.UserDAO;
import com.globant.ecommerce.user.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;
	@Autowired
	RestTemplate restTemplate;
	
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

	@Override
	public boolean authenticate(String authToken) {
		String url="http://localhost:8080/checklogin";
		HttpHeaders headers = new HttpHeaders();
		headers.set("authToken", authToken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<String> resp = restTemplate.exchange(
		    url, HttpMethod.GET, entity, String.class);
		JSONObject jo=null;
		String statusCode=null;
		try {
			jo = new JSONObject(resp.getBody());
			System.out.println(resp.getBody());
			statusCode=jo.getString("statusCode");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(statusCode.equals("200")) {
			return true;
		}
		return false;
	}

}
