package com.coforge.training.examportal.model;

import jakarta.persistence.Column;
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

	@Column(nullable = false)
	private String topic; // Topic name (e.g., Java, C++)

	@Column(nullable = false)
	private Long questionId; 

	@Column(nullable = false)
	private String question;

	@Column(nullable = false)
	private String optionA;

	@Column(nullable = false)
	private String optionB;

	@Column(nullable = false)
	private String optionC;

	@Column(nullable = false)
	private String optionD;

	@Column(nullable = false)
	private String correctAnswer; // Correct option (A, B, C, D)

	// Getters and setters
	// ...
}
