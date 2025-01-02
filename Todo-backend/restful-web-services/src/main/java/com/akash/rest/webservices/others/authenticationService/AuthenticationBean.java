package com.akash.rest.webservices.others.authenticationService;

public class AuthenticationBean {
	private String message;
	
	public AuthenticationBean(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
