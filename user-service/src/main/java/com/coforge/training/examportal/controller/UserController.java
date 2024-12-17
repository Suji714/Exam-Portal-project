package com.coforge.training.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.examportal.model.AnswerRequest;
import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	 @Autowired
	    private UserService userService;
	 
	    @PostMapping("/submit-exam/{topic}")
	    public ResponseEntity<String> submitExam(
	            @RequestParam Long userId,
	            @PathVariable String topic,
	            @RequestBody List<AnswerRequest> answers) {
	 
	        int score = userService.calculateAndStoreScore(userId, topic, answers);
	        return ResponseEntity.ok("Your score for " + topic + " is: " + score);
	    }
	    
	    @PostMapping("/register")
	    public ResponseEntity<?> register(@RequestBody User user){
	    	userService.saveUser(user);
	    	return ResponseEntity.status(HttpStatus.CREATED).body(user);
	    }
	    
	    
}
