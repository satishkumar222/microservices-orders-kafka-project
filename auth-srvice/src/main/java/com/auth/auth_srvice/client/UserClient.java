package com.auth.auth_srvice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.auth.auth_srvice.dto.UserDto;

@FeignClient(name="user-service",url="${user.service.url}")
public interface UserClient {
	@GetMapping("/api/users/email/{email}")
	public UserDto getUserByEmail(@PathVariable String email);

}
