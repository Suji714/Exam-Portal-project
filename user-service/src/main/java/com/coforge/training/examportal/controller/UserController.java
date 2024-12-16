package com.coforge.training.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
import com.coforge.training.examportal.service.ExamService;
import com.coforge.training.examportal.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	private final AuthenticationManager authenticationManager;

	@Autowired
	private ExamService examService;



	public UserController(UserService userService, AuthenticationManager authenticationManager) {
		super();
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}



	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}


	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
					);
			String username = authentication.getName();
			System.out.println("User " + username + " has been authenticated");

			return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
	}
	
    // Submit answers and calculate/store score
    @PostMapping("/submit-exam/{topic}")
    public ResponseEntity<String> submitExam(
            @RequestParam Long userId,
            @PathVariable String topic,
            @RequestBody List<AnswerRequest> answers) {
 
        int score = userService.calculateAndStoreScore(userId, topic, answers);
        return ResponseEntity.ok("Your score for the " + topic + " exam is: " + score);
    }
 
    // Retrieve all scores for a user
    @GetMapping("/scores/{userId}")
    public ResponseEntity<List<UserScore>> getUserScores(@PathVariable Long userId) {
    	List<UserScore> scores=userService.getUserScores(userId);
        return ResponseEntity.ok(scores);
    }
    
	@GetMapping("/")
	public String welcome() {
		return "Welcome";
	}
}
