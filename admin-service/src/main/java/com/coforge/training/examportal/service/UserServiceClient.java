package com.coforge.training.examportal.service;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
* Author      : Satyam.3.Singh
* Date        : 1:03:46 pm
* Time        : 1:03:46 pm
* Project     : admin-service
*/

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
	
	@GetMapping("/api/user/{username}")
	public ResponseEntity<?> findByUsername(String username);

}
