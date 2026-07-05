package com.auth.auth_srvice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthSrviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthSrviceApplication.class, args);
	}

}
