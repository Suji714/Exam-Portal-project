package com.coforge.training.examportal.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coforge.training.examportal.service.AdminService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * Add questions via a JSON file.
	 */
	@PostMapping("/add-questions")
	public ResponseEntity<String> addQuestions(@RequestPart("file") MultipartFile file) {
		try {
			adminService.addQuestions(file);
			return ResponseEntity.ok("Questions uploaded successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		}
	}

	/**
	 * Remove questions by topic.
	 */
	@DeleteMapping("/remove-questions/{topic}")
	public ResponseEntity<String> removeQuestions(@PathVariable String topic) {
		adminService.removeQuestionsByTopic(topic);
		return ResponseEntity.ok("Questions for topic '" + topic + "' removed successfully!");
	}
	
	  // Endpoint for admin to view reports
    @GetMapping("/view-reports")
    public List<Object[]> viewReports(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String firstname) {
        return adminService.viewUserReports(userId, firstname);
    }
   
}
