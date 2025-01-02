package com.akash.rest.webservices.restful_web_services.jwtAuthService;

import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class JwtAuthenticationService {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private JdbcUserDetailsManager jdbcUserDetailsManager;
//	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private JwtEncoder jwtEncoder;
	
	
	public JwtAuthenticationService(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}
	
	@GetMapping("/authenticate") 
	public JwtToken authenticate(Authentication authentication) {
		if(authentication.isAuthenticated()) 
		return new JwtToken(createToken(authentication));
		else {
			
			System.out.println("not authenticated");
			return new JwtToken("no token");
		}
		}

	private String createToken(Authentication authentication) {
		var claims = JwtClaimsSet.builder()
								.issuer("self")
								.issuedAt(Instant.now())
								.expiresAt(Instant.now().plusSeconds(60 * 30))
								.subject(authentication.getName())
								.claim("scope", createScope(authentication))
								.build();
		
		return jwtEncoder.encode(JwtEncoderParameters.from(claims))
							.getTokenValue();
	}
	
	@PostMapping("/signup")
	public Message createUser(@RequestBody com.akash.rest.webservices.restful_web_services.jwtAuthService.User user) {
		        
		if(jdbcUserDetailsManager.userExists(user.getUsername())) {
			return new Message("failure");
		}
		else
		{
			UserDetails user1 = User.withUsername(user.getUsername())
					.password(user.getPassword())
					.passwordEncoder(str -> passwordEncoder.encode(str))
					.authorities("read")
					.roles("USER")
					.build();
			
//        var jdbcUserDetailsManager = new JdbcUserDetailsManager(datasource);
			
			jdbcUserDetailsManager.createUser(user1);
			
			return new Message("success");			
		}
	}


	private String createScope(Authentication authentication) {
		return authentication.getAuthorities().stream()
			.map(a -> a.getAuthority())
			.collect(Collectors.joining(" "));			
	}

}


//create record for returning token in object
record JwtResponse(String token) {}


