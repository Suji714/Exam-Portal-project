package com.coforge.training.examportal.service;

import org.springframework.cloud.openfeign.FeignClient; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//import com.coforge.training.examportal.model.User;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    
	@GetMapping("/api/user/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username); 
}


