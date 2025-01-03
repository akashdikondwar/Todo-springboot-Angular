//package com.akash.rest.webservices.restful_web_services.jwtAuthService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin(origins="http://localhost:4200")
//@RestController
//public class JwtAuthenticationController {
//    
//	@Autowired
//    private final JwtTokenService tokenService;
//    
//    private final AuthenticationManager authenticationManager;
//
//    //constructor
//    public JwtAuthenticationController(JwtTokenService tokenService, 
//            AuthenticationManager authenticationManager) {
//        this.tokenService = tokenService;
//        this.authenticationManager = authenticationManager;
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<JwtTokenResponse> generateToken(
//            @RequestBody JwtTokenRequest jwtTokenRequest) {
//        
//        var authenticationToken = 
//                new UsernamePasswordAuthenticationToken(
//                        jwtTokenRequest.username(), 
//                        jwtTokenRequest.password()
//                        );
//        
//        var authentication = 
//                authenticationManager.authenticate(authenticationToken);
//        
//        var token = tokenService.generateToken(authentication);
//        
//        return ResponseEntity.ok(new JwtTokenResponse(token));
//    }
//}
//
//
