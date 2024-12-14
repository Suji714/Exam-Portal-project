package com.coforge.training.examportal.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
* Author      : Satyam.3.Singh
* Date        : 1:11:01 pm
* Time        : 1:11:01 pm
* Project     : admin-service
*/

@Configuration
public class SecurityConfig {
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
