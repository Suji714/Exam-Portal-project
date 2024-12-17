package com.coforge.training.examportal.model;

import lombok.Data;

@Data
public class AnswerRequest {
	
	private Long questionId; // Question ID
    private String selectedOption; // User-selected option (A, B, C, D)
	
}
