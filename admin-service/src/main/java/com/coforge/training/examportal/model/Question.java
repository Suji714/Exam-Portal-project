package com.coforge.training.examportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/*
* Author      : Satyam.3.Singh
* Date        : 12:41:46 pm
* Time        : 12:41:46 pm
* Project     : admin-service
*/

@Entity
@Data
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String questionText;
	private String answer;
}
