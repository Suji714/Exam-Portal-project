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
    private ExamQuestionRepository examQuestionRepository;
 
    /**
     * Upload and save questions, assigning unique IDs per topic.
     */
    public void saveQuestionsFromJson(MultipartFile file) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ExamQuestion> questions = objectMapper.readValue(file.getInputStream(), new TypeReference<List<ExamQuestion>>() {});
 
        for (ExamQuestion question : questions) {
            Long nextQuestionId = examQuestionRepository.findMaxQuestionIdByTopic(question.getTopic()) + 1;
            question.setQuestionId(nextQuestionId); // Assign unique questionId for the topic
examQuestionRepository.save(question);
        }
    }
 
    /**
     * Retrieve questions by topic.
     */
    public List<ExamQuestion> getQuestionsByTopic(String topic) {
        return examQuestionRepository.findByTopic(topic);
    }
    
    public void deleteQuestionsByTopic(String topic) {
        List<ExamQuestion> questions = examQuestionRepository.findByTopic(topic);
        if (!questions.isEmpty()) {
            examQuestionRepository.deleteAll(questions);
        }
    }
}
