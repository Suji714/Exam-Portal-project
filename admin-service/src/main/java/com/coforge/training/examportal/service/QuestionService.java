package com.coforge.training.examportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coforge.training.examportal.model.Question;
import com.coforge.training.examportal.repository.QuestionRepository;

/*
* Author      : Satyam.3.Singh
* Date        : 1:13:37 pm
* Time        : 1:13:37 pm
* Project     : admin-service
*/

@Service
public class QuestionService {
	
	@Autowired
    private QuestionRepository questionRepository;
 
    // Get a question by ID
    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }
 
    // Save or update a question
    public void saveQuestion(Question question) {
questionRepository.save(question);
    }
 
    // Delete a question by ID
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }
}
