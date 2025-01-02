package com.akash.rest.webservices.others.authenticationService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

//here we haven't yet used cors.

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class AuthenticationService {

	@GetMapping("/authentication")
	public AuthenticationBean basicAuthentication() {
		return new AuthenticationBean("Authenticated Successfully.");
	}
	
}
