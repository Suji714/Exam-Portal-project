package com.coforge.training.examportal.repository;
/*
* Author      : Satyam.3.Singh
* Date        : 14 Dec 2024
* Time        : 9:37:48 am
* Project     : authentication-service
*/

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coforge.training.examportal.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
}
