package com.coforge.training.examportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coforge.training.examportal.model.ExamQuestion;
import com.coforge.training.examportal.repository.ExamQuestionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExamService {

	@Autowired
	private ExamQuestionRepository examRepo;

	public List<ExamQuestion> getQuestionByExamTopic(String topic) {
		return examRepo.findByExamTopic(topic);
	}

	public void saveQuestionsFromJson(MultipartFile file) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		// Parse JSON file
		List<ExamQuestion> questions = objectMapper.readValue(file.getInputStream(), new TypeReference<List<ExamQuestion>>() {});

		// Validate and save each question
		for (ExamQuestion question : questions) {
			if (isValidQuestion(question)) {
				examRepo.save(question);
			} else {
				throw new IllegalArgumentException("Invalid question format for ID: " + question.getId());
			}
		}
	}

	private boolean isValidQuestion(ExamQuestion question) {
		// Basic validation logic for question format
		return question.getQuestion() != null && !question.getQuestion().isEmpty()
				&& question.getCorrectAnswer() != null && !question.getCorrectAnswer().isEmpty()
				&& question.getOptionA() != null && question.getOptionB() != null
				&& question.getOptionC() != null && question.getOptionD() != null;
	}
}
