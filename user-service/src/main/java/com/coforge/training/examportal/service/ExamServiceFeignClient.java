package com.coforge.training.examportal.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.coforge.training.examportal.model.ExamQuestion;

@FeignClient(name = "exam-service")
public interface ExamServiceFeignClient {

	//get exam questions by topic
	@GetMapping("/api/exam/questions/{topic}")
	public List<ExamQuestion> getQuestionsByTopic(@PathVariable String topic);

	
	//upload questions in json file by admin
	@PostMapping(value = "/api/exam/upload-questions", consumes = "multipart/form-data")
    void uploadQuestions(@RequestParam("file") MultipartFile file);
 
	//delete questions based on the topic
    @DeleteMapping("/api/exam/delete-questions/{topic}")
    void deleteQuestionsByTopic(@PathVariable String topic);

}
