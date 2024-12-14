package com.coforge.training.examportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
 
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public User saveUser(User user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
return userRepository.save(user);
    }
 
    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }
 
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
