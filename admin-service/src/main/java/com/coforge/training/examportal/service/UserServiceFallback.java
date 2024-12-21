package com.coforge.training.examportal.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserServiceClient {

	
	@Override
	public List<Object[]> fetchUserReports(Long userId, String firstname) {
		// Return an empty list or an appropriate message
		System.out.println("User Service is down. Returning fallback response.");
		return Collections.emptyList(); // Optionally, log the failure
	}
	
}

