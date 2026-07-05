package com.auth.auth_srvice.service;

import org.springframework.stereotype.Service;

import com.auth.auth_srvice.client.UserClient;
import com.auth.auth_srvice.dto.LoginRequestDto;
import com.auth.auth_srvice.dto.LoginResponse;
import com.auth.auth_srvice.dto.UserDto;
import com.auth.auth_srvice.util.JwtUtil;

@Service
public class AuthService {
	
	private final UserClient userClient;
	private JwtUtil jwtUtil;
	
	public AuthService(UserClient userClient, JwtUtil jwtUtil) {
		super();
		this.userClient = userClient;
		this.jwtUtil = jwtUtil;
	} 
	public LoginResponse login(LoginRequestDto request ) {
		
		// user validation
		UserDto user=userClient.getUserByEmail(request.getEmail());
		if(user==null) {
			 throw new RuntimeException("user nort found");
			
		}
		
		  // Password Validation
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }
		
        //generate JWT token
        String token=jwtUtil.generateToken(user.getId(),user.getEmail(), user.getRole());
        
        
        //prepare response
        LoginResponse response=new LoginResponse();
        response.setToken(token);
        
		return response ;
		
	}
	
	
}
