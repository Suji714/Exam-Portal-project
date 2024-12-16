package com.coforge.training.examportal.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.coforge.training.examportal.model.ExamQuestion;

@FeignClient(name = "exam-service")
public interface ExamServiceFeignClient {

	@GetMapping("/api/exam/question/{examTopic}")
	public List<ExamQuestion> questionByExamTopic(@PathVariable String examTopic);

//	public ExamQuestion getQuestionById(Long questionId);

}
