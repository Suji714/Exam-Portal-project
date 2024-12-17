package com.coforge.training.examportal.service;
/*
* Author      : Satyam.3.Singh
* Date        : 14 Dec 2024
* Time        : 10:08:03 am
* Project     : authentication-service
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
    private UserRepository userRepository;
 
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
 
    @Autowired
    private JwtUtil jwtUtil;
 
    // Register User
    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
userRepository.save(user);
        return "User registered successfully";
    }
 
    // Login User
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return jwtUtil.generateToken(email, "USER");
    }
 
    // Admin Login
    public String loginAdmin(String username, String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            return jwtUtil.generateToken(username, "ADMIN");
        }
        throw new RuntimeException("Invalid admin credentials");
    }
}
