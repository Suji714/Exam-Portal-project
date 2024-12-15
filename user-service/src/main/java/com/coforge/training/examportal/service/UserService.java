package com.coforge.training.examportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;

	//Di using conatructor
	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}


	//    @Autowired
	//    private PasswordEncoder passwordEncoder;

	//    public User saveUser(User user) {
	//        // Encode the password before saving
	//        user.setPassword(passwordEncoder.encode(user.getPassword()));
	//        return userRepository.save(user);
	//    }


	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user); //save() pre-defined method in JPA repo
	}


//	public User findUser(String username) {
//		return userRepo.findByUsername(username);
//	}
	//
	//    public boolean checkPassword(String rawPassword, String encodedPassword) {
	//        return passwordEncoder.matches(rawPassword, encodedPassword);
	//    }
}
