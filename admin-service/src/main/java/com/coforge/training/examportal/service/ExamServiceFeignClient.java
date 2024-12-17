package com.coforge.training.examportal.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "exam-service")
public interface ExamServiceFeignClient {
 
//	 @PostMapping("/upload-questions")
//	    public ResponseEntity<String> uploadQuestions(@RequestParam MultipartFile file) 
	
    @PostMapping("/api/exam/upload-questions")
    public ResponseEntity<String> uploadQuestions(@RequestParam MultipartFile file);
 
    @DeleteMapping("/api/exam/remove-questions/{topic}")
    void removeQuestionsByTopic(@PathVariable String topic);
}