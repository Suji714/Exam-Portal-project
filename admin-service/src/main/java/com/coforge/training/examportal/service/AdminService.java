package com.coforge.training.examportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminService {

	@Autowired
	private ExamServiceFeignClient examServiceFeignClient;

	@Autowired
	private UserServiceClient userServiceClient;

	/**
	 * Add questions to the ExamService.
	 */
	public void addQuestions(MultipartFile file) {
		examServiceFeignClient.addQuestions(file);
	}

	/**
	 * Remove questions by topic in the ExamService.
	 */
	public void removeQuestionsByTopic(String topic) {
		examServiceFeignClient.removeQuestionsByTopic(topic);
	}

	/**
	 * View reports of all Users by admin
	 */
	public List<Object[]> viewUserReports(Long userId, String firstname) {
		return userServiceClient.fetchUserReports(userId, firstname);
	}
}
