package com.coforge.training.examportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.examportal.exception.ResourceNotFoundException;
import com.coforge.training.examportal.model.User;
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

	//    @PostMapping("/exam/submit")
	//    public ResponseEntity<?> submitExam(@RequestParam String username, @RequestParam boolean passed) {
	//        String result = examService.submitExamResult(username, passed);
	//
	//        if (result.equals("Congratulations, you have passed all levels!")) {
	//            return ResponseEntity.ok(result);
	//        }
	//
	//        if (result.contains("You failed the exam")) {
	//            return ResponseEntity.status(HttpStatus.OK).body(result);
	//        }
	//
	//        return ResponseEntity.status(HttpStatus.OK).body(result);
	//    }

	//    @PostMapping("/logout")
	//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
	//        return ResponseEntity.status(HttpStatus.OK).body("You have been logged out successfully");
	//    }

	//    @GetMapping("/{username}")
	//    public ResponseEntity<?> findByUsername(@PathVariable String username) throws ResourceNotFoundException {
	//        User user = userService.findUser(username);
	//        if (user != null) {
	//            return ResponseEntity.ok(user);
	//        }
	//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	//    }


	@GetMapping("/")
	public String welcome() {
		return "Welcome";
	}
}
