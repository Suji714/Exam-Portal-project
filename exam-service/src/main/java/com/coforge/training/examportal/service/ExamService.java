package com.coforge.training.examportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coforge.training.examportal.model.ExamQuestion;
import com.coforge.training.examportal.repository.ExamQuestionRepository;

@Service
public class ExamService {
	
	private ExamQuestionRepository examRepo;
	
	public List<ExamQuestion> getQuestionByExamTopic(String topic) {
		return examRepo.findByExamTopic(topic);
	}
	
	public void addQuestion(ExamQuestion question) {
		examRepo.save(question);
	}
}
