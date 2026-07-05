package com.auth.auth_srvice.dto;

import lombok.Data;

@Data
public class UserDto {
	 private Long id;

	    private String email;

	    private String password;

	    private String role;
}
