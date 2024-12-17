package com.coforge.training.examportal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.service.AuthService;

/*
* Author      : Satyam.3.Singh
* Date        : 14 Dec 2024
* Time        : 10:18:35 am
* Project     : authentication-service
*/

@RequestMapping("/auth")
@RestController
public class AuthController {
	
	 @Autowired
	    private AuthService authService;
	 
	    @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@RequestBody User user) {
	        return ResponseEntity.ok(authService.registerUser(user));
	    }
	 
	    @PostMapping("/login/user")
	    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> credentials) {
	        String email = credentials.get("email");
	        String password = credentials.get("password");
	        return ResponseEntity.ok(authService.loginUser(email, password));
	    }
	 
	    @PostMapping("/login/admin")
	    public ResponseEntity<String> loginAdmin(@RequestBody Map<String, String> credentials) {
	        String username = credentials.get("username");
	        String password = credentials.get("password");
	        return ResponseEntity.ok(authService.loginAdmin(username, password));
	    }
}
