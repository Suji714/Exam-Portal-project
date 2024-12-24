package com.coforge.training.examportal.service;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "user-service", url = "http://localhost:8083")
public interface UserServiceClient {
 
	//To fetch reports of all users by the admin
    @GetMapping("/api/user/reports")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUserReports")
    ResponseEntity<List<Object[]>> getUserReports(@RequestParam("userId") Long userId, @RequestParam("firstname") String firstname);
    
    default ResponseEntity<List<Object[]>> fallbackGetUserReports(@RequestParam("userId") Long userId, @RequestParam("firstname") String firstname, Throwable throwable) {        
    	System.out.println("User Service is down: " + throwable.getMessage());
        return ResponseEntity.status(503).body(Collections.emptyList());
    }
}

   

