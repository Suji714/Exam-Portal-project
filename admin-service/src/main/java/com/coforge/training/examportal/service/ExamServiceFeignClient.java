package com.coforge.training.examportal.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "exam-service", configuration = FeignConfig.class)
public interface ExamServiceFeignClient {

	//To Add questions to the database by Admin
	@PostMapping(value = "/api/exam/add-questions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	void addQuestions(@RequestPart("file") MultipartFile file);

	//To remove questions from the database specific to topic by Admin
	@DeleteMapping("/api/exam/remove-questions/{topic}")
	void removeQuestionsByTopic(@PathVariable String topic);
}