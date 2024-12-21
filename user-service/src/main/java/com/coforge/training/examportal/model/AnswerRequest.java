package com.coforge.training.examportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {
	
	private Long questionId; // Question ID
    private String selectedOption; // User-selected option (A, B, C, D)
	
}
