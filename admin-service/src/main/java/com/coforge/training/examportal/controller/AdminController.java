package com.coforge.training.examportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.examportal.model.Question;
import com.coforge.training.examportal.service.QuestionService;
import com.coforge.training.examportal.service.UserServiceClient;

/*
* Author      : Satyam.3.Singh
* Date        : 1:00:11 pm
* Time        : 1:00:11 pm
* Project     : admin-service
*/

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	 @Autowired
	    private UserServiceClient userService;
	 
	    @Autowired
	    private QuestionService questionService; // Service to handle questions (add, update, delete)
	 
	    @Autowired
	    private BCryptPasswordEncoder passwordEncoder;
	 
	    private static final String ADMIN_USERNAME = "admin";  // Static admin username
	    private static final String ADMIN_PASSWORD = "admin123";  // Static admin password (not recommended for production)
	 
	    // Admin login endpoint - No need for JWT, just validate static credentials
	    @PostMapping("/login")
	    public ResponseEntity<?> adminLogin(@RequestParam String username, @RequestParam String password) {
	        // Validate admin credentials (Static username/password)
	        if (ADMIN_USERNAME.equals(username) && passwordEncoder.matches(password, ADMIN_PASSWORD)) {
	            return ResponseEntity.ok("Admin logged in successfully");
	        }
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin credentials");
	    }
	 
	    // Endpoint to update the question (admin only)
	    @PutMapping("/update-question")
	    public ResponseEntity<?> updateQuestion(@RequestParam Long questionId, @RequestBody Question updatedQuestion) {
	        // Check if the question exists and update
	        Question question = questionService.getQuestionById(questionId);
	 
	        if (question == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
	        }
	 
	        question.setQuestionText(updatedQuestion.getQuestionText());
	        question.setAnswer(updatedQuestion.getAnswer());
	 
	        questionService.saveQuestion(question);  // Save updated question
	        return ResponseEntity.ok("Question updated successfully");
	    }
	 
	    // Admin can fetch user details to check their level and exam status
	    @GetMapping("/user/{username}")
	    public ResponseEntity<?> getUserDetails(@PathVariable String username) {
	        // Get user details from User Microservice
	        User user = userService.find(username);
	 
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }
	 
	        return ResponseEntity.ok(user);  // Return the user details (excluding password)
	    }
	 
	    // Admin can view and update the user's level (for promoting users)
	    @PutMapping("/promote-user/{username}")
	    public ResponseEntity<?> promoteUser(@PathVariable String username) {
	        User user = userService.findByUsername(username);
	 
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }
	 
	        // If user passed level 3, they have completed the exam process
	        if (user.getLevel() == 3) {
	            return ResponseEntity.ok("User has already completed all levels. Congratulations!");
	        }
	 
	        user.setLevel(user.getLevel() + 1);  // Promote user to the next level
	        userService.saveUser(user);  // Save the updated user data
	        return ResponseEntity.ok("User promoted to level " + user.getLevel());
	    }
}
