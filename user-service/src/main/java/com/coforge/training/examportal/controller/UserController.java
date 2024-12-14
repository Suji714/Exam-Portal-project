package com.coforge.training.examportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.examportal.exception.ResourceNotFoundException;
import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.service.ExamService;
import com.coforge.training.examportal.service.UserService;

/*
* Author      : Satyam.3.Singh
* Date        : 12:30:10 pm
* Time        : 12:30:10 pm
* Project     : user-service
*/

@EnableFeignClients
@RestController
@RequestMapping("/api/user")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ExamService examService;
 
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findUser(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        }
        user.setRole("user");
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
 
 // Login user and verify password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // Find user by username
        User user = userService.findUser(username);
 
        // If user doesn't exist or password is incorrect
        if (user == null || !userService.checkPassword(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
 
        // User login successful, return user data (exclude password)
        user.setPassword(null);  // Remove the password from the response
        return ResponseEntity.ok(user);
    }
 
    // Endpoint to submit exam result (true for pass, false for fail)
    @PostMapping("/exam/submit")
    public ResponseEntity<?> submitExam(@RequestParam String username, @RequestParam boolean passed) {
        String result = examService.submitExamResult(username, passed);
        
        // If level 3 is completed, inform the user they passed all levels
        if (result.equals("Congratulations, you have passed all levels!")) {
            return ResponseEntity.ok(result);
        }
        
        // If the user failed, prompt for logout
        if (result.contains("You failed the exam")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
 
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
 
    // Endpoint to logout the user (clear the session or token)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        // Invalidate the JWT token or session
        // Code to invalidate the token (JWT or session management based on your implementation)
        
        return ResponseEntity.status(HttpStatus.OK).body("You have been logged out successfully");
    }
    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) throws ResourceNotFoundException{
        User user = userService.findUser(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
