package com.coforge.training.examportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coforge.training.examportal.model.Question;

/*
* Author      : Satyam.3.Singh
* Date        : 1:25:04 pm
* Time        : 1:25:04 pm
* Project     : admin-service
*/

public interface QuestionRepository extends JpaRepository<Question, Long>{
	
}
