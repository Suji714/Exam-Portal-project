package com.coforge.training.examportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ExamQuestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String examTopic;
	
	private String question;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	
	private String correctAnswer;
}
