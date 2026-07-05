package com.auth.auth_srvice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth_srvice.dto.LoginRequestDto;
import com.auth.auth_srvice.dto.LoginResponse;
import com.auth.auth_srvice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	
    @PostMapping("/login")
	public   ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto request) {
    	LoginResponse response=authService.login(request);
		return ResponseEntity.ok(response);
		
	}

}
