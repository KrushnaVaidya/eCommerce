package com.globant.ecommerce.user.controller;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.globant.ecommerce.user.facade.UserFacadeImpl;
import com.globant.ecommerce.user.model.User;

@RestController
public class UserController {
	@Autowired
	private UserFacadeImpl userFacade;
	@PostMapping("/user")
	public ResponseEntity<User> register(@RequestBody User user) throws Exception{	
		HttpHeaders headers = new HttpHeaders();
		if(user.getEmail()!=null && user.getPassword()!=null) {			
			User userAvailability=userFacade.findByEmail(user.getEmail());			
			if(userAvailability!=null) {
				headers.add("message", "User Already Registered");
				userAvailability.setPassword("");
				return new ResponseEntity<>(userAvailability,headers, HttpStatus.OK);	
			}
			else {
				user.setStatus("Active");
				user.setType("Customer");
				User registeredUser=userFacade.register(user);
				if(registeredUser!=null) {
					headers.add("message", "User Registered Successfully");
					registeredUser.setPassword("");
					return new ResponseEntity<>(registeredUser,headers, HttpStatus.CREATED);
				}
				else {
					headers.add("message", "User Registered Failed");
					return new ResponseEntity<>(registeredUser,headers, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
	
		headers.add("message", "Invalid Data");
		return new ResponseEntity<>(null,headers, HttpStatus.OK);
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<User> Login(@RequestBody String reqBody)throws Exception{
		JSONObject jo = null;
		String email = null,password = null;
		try {
			jo = new JSONObject(reqBody);
			email=jo.getString("email");
			password=jo.getString("password");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		User auser=userFacade.findByEmail(email);
		if(auser!=null) {
			if(auser.getAuthToken().equals("")) {
				User user=userFacade.login(email,password);
				headers.add("message", "User Login Successful");
				user.setPassword("");
				return new ResponseEntity<>(user,headers, HttpStatus.OK);
			}
			headers.add("message", "User Already Login");
			auser.setPassword("");
			return new ResponseEntity<>(auser,headers, HttpStatus.OK);
			
		}
		headers.add("message", "User Not Register");
		return new ResponseEntity<>(null,headers, HttpStatus.NOT_FOUND);
		 
	}
	
	@PutMapping("/user/{userid}")
	public ResponseEntity<User> update(@RequestBody User user,@PathVariable("userid")int id,@RequestHeader(value = "authToken",defaultValue = "")String authToken)throws Exception {
		HttpHeaders headers=new HttpHeaders();
		if(userFacade.authenticate(authToken)) {
			User userAvailability=userFacade.findById(id);			
			if(userAvailability!=null) {
				user.setId(id);
				user.setStatus("Active");
				user.setType("Customer");
				User updatedUser=userFacade.update(user);
				updatedUser.setPassword("");;
				headers.add("message", "User Updated Successfully");
				return new ResponseEntity<>(updatedUser,headers, HttpStatus.OK);	
			}
			else {
				headers.add("message", "User Not Found");
				return new ResponseEntity<>(userAvailability,headers, HttpStatus.NOT_FOUND);	
			}
		}
		headers.add("message", "User Not Logged In");
		return new ResponseEntity<>(null,headers, HttpStatus.UNAUTHORIZED);
	}
	@PutMapping("/user/changepassword/{userid}")
	public ResponseEntity<User> updatePassword(@RequestBody String reqBoby, @PathVariable("userid")int id,@RequestHeader(value = "authToken",defaultValue = "")String authToken) throws Exception{
		HttpHeaders headers =new HttpHeaders();
		if(userFacade.authenticate(authToken)) {
			JSONObject jo = null;
			jo = new JSONObject(reqBoby);
			String nPassword=jo.getString("new_password");
			String cPassword=jo.getString("current_password");
			if(nPassword.equals("") && cPassword.equals("")) {
				headers.add("message", "Invalid Data");
				return new ResponseEntity<>(null,headers, HttpStatus.BAD_REQUEST);
			}
			else {
				User userAvailability=userFacade.findById(id);
				 if(userAvailability!=null) { 
					 if(userAvailability.getPassword().equals(cPassword)) {
						 userAvailability.setPassword(nPassword);
						 User updatedUser=userFacade.update(userAvailability); 
						 headers.add("message", "User Upadted Successfully");
						 return new ResponseEntity<>(updatedUser,headers, HttpStatus.OK); 
					 }
					 else {
						 headers.add("message", "Current Password Mismatch");
						 return new ResponseEntity<>(null,headers, HttpStatus.OK);
					 }
				 }
				 else {
					 headers.add("message", "User Not Found");
						return new ResponseEntity<>(null,headers, HttpStatus.NOT_FOUND); 
				 }
			}				 
		}
		headers.add("message", "User Not Logged In");
		return new ResponseEntity<>(null,headers, HttpStatus.UNAUTHORIZED);
		
	}
	@GetMapping("/user/{userid}")
	public ResponseEntity<User> getUserById(@PathVariable("userid")int id,@RequestHeader(value = "authToken",defaultValue = "")String authToken) throws Exception {
		HttpHeaders headers=new HttpHeaders();
		if(userFacade.authenticate(authToken)) {
			User userAvailability=userFacade.findById(id);
			if(userAvailability!=null) {
				headers.add("message", "User Details");
				return new ResponseEntity<>(userAvailability,headers, HttpStatus.OK);	
			}
			else {
				headers.add("message", "User Not Found");
				return new ResponseEntity<>(null,headers, HttpStatus.NOT_FOUND);
			}
			
		}
		headers.add("message", "User Not Logged In");
		return new ResponseEntity<>(null,headers, HttpStatus.UNAUTHORIZED);
	}
	@PostMapping("/user/logout")
	public ResponseEntity<User> logout(@RequestHeader(value = "authToken",defaultValue = "")String authToken)throws Exception{
		User userAvailability=userFacade.checkUserLoginStatus(authToken);
		HttpHeaders headers=new HttpHeaders();
		if(userAvailability!=null) {
			if(userAvailability.getAuthToken().equals("")) {
				headers.add("message", "User Not Logged In");
				return new ResponseEntity<>(null,headers, HttpStatus.UNAUTHORIZED);
			}
			else {
				userAvailability.setAuthToken(null);
				userAvailability=userFacade.update(userAvailability);
				headers.add("message", "User Log Out Successful");
				return new ResponseEntity<>(null,headers, HttpStatus.OK);
	
			}
		}
		headers.add("message", "User Not Logged In");
		return new ResponseEntity<>(null,headers, HttpStatus.UNAUTHORIZED);
		
	}
	@GetMapping("/user/getallusers")
	public ResponseEntity<List<User>> getAllUsers(@RequestHeader(value = "authToken",defaultValue = "")String authToken)throws Exception{
		HttpHeaders headers=new HttpHeaders();
		if(userFacade.authenticate(authToken)) {
			List<User> data=null;		
			data=userFacade.findAllUsers();
			headers.add("message", "User Details");
			return new ResponseEntity<>(data,headers, HttpStatus.OK);
		}
		headers.add("message", "User Not Logged In");
		return new ResponseEntity<>(null,headers, HttpStatus.UNAUTHORIZED);
		
	}
	@GetMapping("/checklogin")
	public ResponseEntity<User> checkLoginStatus(@RequestHeader(value = "authToken",defaultValue = "")String authToken) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		User user=new User();
		user=userFacade.checkUserLoginStatus(authToken);
		if(user!=null) {
			headers.add("message", "User Login Details");
			user.setPassword("");
			return new ResponseEntity<>(user,headers, HttpStatus.OK);
		}
		headers.add("message", "User Not Logged In");
		return new  ResponseEntity<>(user,headers,HttpStatus.UNAUTHORIZED);
	}
	
	
}
