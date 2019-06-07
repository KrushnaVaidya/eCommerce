package com.globant.ecommerce.user.responses;
import com.globant.ecommerce.user.model.User;
public class LoginResponse {
	private String message;
	private User data;
	private String statusCode;
	
	public LoginResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getData() {
		return data;
	}

	public void setData(User data) {
		this.data = data;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	

}
