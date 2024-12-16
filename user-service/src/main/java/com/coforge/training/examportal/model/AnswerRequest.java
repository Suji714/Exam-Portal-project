package com.coforge.training.examportal.model;

import lombok.Data;

@Data
public class AnswerRequest {
	
	private Long questionId;
	
	private String selectedOption;
	
	
}
