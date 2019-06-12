package com.globant.ecommerce.user.controller;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.globant.ecommerce.user.facade.UserFacadeImpl;
import com.globant.ecommerce.user.model.User;
import com.globant.ecommerce.user.responses.LoginResponse;
import com.globant.ecommerce.user.responses.Response;

@RestController
public class UserController {
	@Autowired
	private UserFacadeImpl userFacade;
	@PostMapping("/user/register")
	public Response<User> register(@RequestBody User user) throws Exception{
		Response<User> response=new Response<User>();
		List<User> data=new ArrayList<User>();
		if(!user.getEmail().equals("") && !user.getPassword().equals("")) {			
			User userAvailability=userFacade.findByEmail(user.getEmail());			
			if(userAvailability!=null) {
				System.out.println(userAvailability);
				data.add(userAvailability);
				response.setStatusCode("200");
				response.setMessage("User Already Registered");
				response.setData(data);
			}
			else {
				user.setStatus("Active");
				user.setType("Customer");
				User registeredUser=userFacade.register(user);
				if(registeredUser!=null) {
					data.add(registeredUser);
					response.setStatusCode("201");
					response.setMessage("User Registered Successfully");
					response.setData(data);
				}
				else {
					response.setStatusCode("500");
					response.setMessage("User Registeration Failed");
					response.setData(data);
				}
			}
			
		}
		else {
			response.setMessage("Invalid Data");
			response.setData(data);
			response.setStatusCode("200");	
		}
		return response;
	}
	
	@PostMapping("/user/login")
	public Response<User> Login(@RequestBody String reqBody)throws Exception{
		JSONObject jo = null;
		String email = null,password = null;
		Response<User> response=new Response<User>();
		try {
			jo = new JSONObject(reqBody);
			email=jo.getString("email");
			password=jo.getString("password");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<User> data=new ArrayList<User>();
		User auser=userFacade.findByEmail(email);
		if(auser!=null) {
			if(auser.getAuthToken()==null) {
				User user=userFacade.login(email, password);
				if(user!=null) {
					data.add(user);
					response.setData(data);
					response.setStatusCode("200");
					response.setMessage("User Logged In Successfully");
				}
				else {
					response.setStatusCode("401");
					response.setMessage("Authentication Failed ");
				}
					
			}
			else {
				response.setMessage("User Already Login");
				data.add(auser);
				response.setData(data);
				response.setStatusCode("200");
			}
			
			return 	response;
		}
		response.setMessage("User Not Registered");
		response.setStatusCode("401");
		return response;	
	}
	
	@PutMapping("/user/update/{userid}")
	public Response<User> update(@RequestBody User user,@PathVariable("userid")int id,@RequestHeader(value = "authToken",defaultValue = "")String authToken)throws Exception {
		Response<User> response=new Response<User>();
		if(userFacade.authenticate(authToken)) {
			User userAvailability=userFacade.findById(id);
			List<User> data=new ArrayList<User>();			
			if(userAvailability!=null) {
				user.setId(id);
				user.setStatus("Active");
				user.setType("Customer");
				User upatedUser=userFacade.update(user);
				data.add(upatedUser);
				response.setMessage("User Updated Successfully");
				response.setData(data);
				response.setStatusCode("200");
				
			}
			else {
				response.setMessage("User Not Found");
				response.setData(data);
				response.setStatusCode("404");;
			}
			
			return response;
		}
		response.setMessage("User Not Logged In");
		response.setStatusCode("401");
		return response;
	}
	@PutMapping("/user/changepassword/{userid}")
	public Response<User> updatePassword(@RequestBody String reqBoby, @PathVariable("userid")int id,@RequestHeader(value = "authToken",defaultValue = "")String authToken) throws Exception{
		Response<User> response=new Response<User>();
		if(userFacade.authenticate(authToken)) {
			JSONObject jo = null;
			jo = new JSONObject(reqBoby);
			List<User> data=new ArrayList<User>();
			String nPassword=jo.getString("new_password");
			String cPassword=jo.getString("current_password");
			if(nPassword.equals("") && cPassword.equals("")) {
				response.setMessage("Invalid Data");
				response.setStatusCode("204");
				response.setData(data);
			}
			else {
				User userAvailability=userFacade.findById(id);
				 if(userAvailability!=null) { 
					 if(userAvailability.getPassword().equals(cPassword)) {
						 userAvailability.setPassword(nPassword);
						 User upatedUser=userFacade.update(userAvailability); 
						 data.add(upatedUser);
						 response.setMessage("User Password Changed Successfully"); 
						 response.setData(data);
						 response.setStatusCode("200"); 
					 }
					 else {
						 response.setMessage("Current Password Mismatch"); 
						 response.setData(data);
						 response.setStatusCode("200"); 
					 }
				 }
				 else {
					 response.setMessage("User Not Found"); 
					 response.setData(data);
					 response.setStatusCode("404"); 
				 }
			}				 
			return response;
		}
		response.setStatusCode("401");
		response.setMessage("User Not Logged In");
		return response;
		
	}
	@GetMapping("/user/getuser/{userid}")
	public Response<User> getUserById(@PathVariable("userid")int id,@RequestHeader(value = "authToken",defaultValue = "")String authToken) throws Exception {
		Response<User> response=new Response<User>();
		System.out.println(userFacade.authenticate(authToken));
		
		if(userFacade.authenticate(authToken)) {
			User userAvailability=userFacade.findById(id);
			List<User> data=new ArrayList<User>();
			if(userAvailability!=null) {
				data.add(userAvailability);
				response.setMessage("User Details");
				response.setData(data);
				response.setStatusCode("200");
				
			}
			else {
				response.setMessage("User Not Found");
				response.setData(data);
				response.setStatusCode("404");;
			}
			return response;
		}
		response.setMessage("User Not Logged In");
		response.setStatusCode("401");
		return response;	
	}
	@PostMapping("/user/logout/{userid}")
	public Response<User> logout(@PathVariable("userid")int id)throws Exception{
		User userAvailability=userFacade.findById(id);
		List<User> data=new ArrayList<User>();
		Response<User> response=new Response<User>();
		if(userAvailability!=null) {
			System.out.println(userAvailability.getEmail());
			if(userAvailability.getAuthToken()==null) {
				response.setMessage("User Not Logged In");
			}
			else {
				userAvailability.setAuthToken(null);
				userAvailability=userFacade.update(userAvailability);
				response.setMessage("User Log Out Successfully");
			}
			response.setData(data);
			response.setStatusCode("200");
		}
		else {
			response.setMessage("User Not Found");
			response.setData(data);
			response.setStatusCode("404");
		}
		return response;
	}
	@GetMapping("/user/getallusers")
	public Response<User> getAllUsers(@RequestHeader(value = "authToken",defaultValue = "")String authToken)throws Exception{
		Response<User> response=new Response<User>();
		if(userFacade.authenticate(authToken)) {
			List<User> data=null;		
			response.setMessage("All Users Details");
			response.setStatusCode("200");
			data=userFacade.findAllUsers();
			response.setData(data);
			return response;
		}
		response.setMessage("User Not Logged In");
		response.setStatusCode("401");
		return response;
		
	}
	@GetMapping("/checklogin")
	public LoginResponse checkLoginStatus(@RequestHeader(value = "authToken",defaultValue = "")String authToken) throws Exception{
		LoginResponse response=new LoginResponse();
		User user=userFacade.checkUserLoginStatus(authToken);
		if(user!=null) {
			response.setMessage("User Log In Details");
			response.setStatusCode("200");
			response.setData(user);
		}
		else {
			response.setMessage("User Not Logged In");
			response.setStatusCode("401");
			response.setData(null);
		}
		return response;
	}
	
	
}
