package com.coforge.training.examportal.controller;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.examportal.model.AnswerRequest;
import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.model.UserScore;
import com.coforge.training.examportal.repository.UserRepository;
import com.coforge.training.examportal.service.UserScoreService;
import com.coforge.training.examportal.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	 @Autowired
	    private UserService userService;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	    private UserScoreService userScoreService;
	 
	 //registering the user
	    @PostMapping("/register")
	    public ResponseEntity<?> register(@RequestBody User user) {
	        try {
	            userService.saveUser(user);
	            return ResponseEntity.status(HttpStatus.CREATED).body(user);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
	    
	    
	    // Authenticate User login
	    // http://localhost:8083/api/user/login
	    // Authenticate User login
	    @PostMapping("/login")
	    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody User loginRequest) {
	        User user = userRepository.findByEmail(loginRequest.getEmail());
	        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("message", "Login successful!");
	            response.put("userId", user.getId());
	            return ResponseEntity.ok(response); 
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid credentials!"));
	        }
	    }
 
	 
	
	 //submit exam based on topic after users click submit
	    @PostMapping("/submit-exam/{topic}")
	    public ResponseEntity<String> submitExam(
	            @RequestParam Long userId,
	            @PathVariable String topic,
	            @RequestBody List<AnswerRequest> answers) {
	 
	        int score = userService.calculateAndStoreScore(userId, topic, answers);
	        return ResponseEntity.ok("Your score for " + topic + " is: " + score);
	    }
	    
	
	    // Endpoint to view reports
	    @GetMapping("/reports")
	    public List<Object[]> getUserReports(
	            @RequestParam(required = false) Long userId,
	            @RequestParam(required = false) String firstname) {
	        return userService.getUserReports(userId, firstname); 
	    }
	    
	    
	    /**
	     * Fetch the report of scores for all topics for a particular user.
	     */
	    @GetMapping("/report/{userId}")
	    public ResponseEntity<List<UserScore>> getUserReport(@PathVariable Long userId) {
	        List<UserScore> userScores = userService.getUserScores(userId);
	        return ResponseEntity.ok(userScores);
	    }
	 
	    @GetMapping("/score/{userId}/download")
	    public ResponseEntity<byte[]> downloadUserScorePdf(@PathVariable Long userId) {
	        ByteArrayInputStream bis = userScoreService.generateUserScorePdf(userId);

	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=UserScore.pdf");

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(bis.readAllBytes());
	    }

}
