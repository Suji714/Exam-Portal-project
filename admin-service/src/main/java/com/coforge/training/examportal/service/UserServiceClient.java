package com.coforge.training.examportal.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", fallback = UserServiceFallback.class)
public interface UserServiceClient {
 
	//To fetch reports of all users by the admin
    @GetMapping("api/user/reports")
    List<Object[]> fetchUserReports(@RequestParam(required = false) Long userId,
                                    @RequestParam(required = false) String firstname);
    
 
}