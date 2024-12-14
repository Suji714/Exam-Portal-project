package com.coforge.training.examportal.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
* Author      : Satyam.3.Singh
* Date        : 12:36:44 pm
* Time        : 12:36:44 pm
* Project     : user-service
*/

@Configuration
public class SecurityConfig {
	
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}
