package com.coforge.training.examportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coforge.training.examportal.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
		
	public User findByUsername (String username);
}
