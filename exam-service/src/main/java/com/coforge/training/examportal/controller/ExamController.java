package com.coforge.training.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coforge.training.examportal.model.ExamQuestion;
import com.coforge.training.examportal.service.ExamService;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
	
    @Autowired
    private ExamService examService;
 
    /**
     * Upload questions from a JSON file.
     */
    @PostMapping("/upload-questions")
    public ResponseEntity<String> uploadQuestions(@RequestParam("file") MultipartFile file) {
        try {
            examService.saveQuestionsFromJson(file);
            return ResponseEntity.ok("Questions uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
 
    /**
     * Get questions by topic.
     */
    @GetMapping("/questions/{topic}")
    public ResponseEntity<List<ExamQuestion>> getQuestionsByTopic(@PathVariable String topic) {
        return ResponseEntity.ok(examService.getQuestionsByTopic(topic));
    }
     
}
