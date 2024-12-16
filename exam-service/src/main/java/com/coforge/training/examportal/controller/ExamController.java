package com.coforge.training.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.examportal.model.ExamQuestion;
import com.coforge.training.examportal.service.ExamService;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
	
	@Autowired
    private ExamService examService;
 
    @GetMapping("/question/{examTopic}")
    public ResponseEntity<List<ExamQuestion>> questionByExamTopic(@PathVariable String examTopic) {
        return ResponseEntity.ok(examService.getQuestionByExamTopic(examTopic));
    }
 
    @PostMapping("/add-question")
    public ResponseEntity<String> addQuestion(@RequestBody ExamQuestion question) {
        examService.addQuestion(question);
        return ResponseEntity.ok("Question added successfully!");
    }
}
