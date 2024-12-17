package com.coforge.training.examportal.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {
 
    @GetMapping("api/user/reports")
    List<Object[]> fetchUserReports(@RequestParam(required = false) Long userId,
                                    @RequestParam(required = false) String firstname);
    
    
// // Endpoint to view reports
//    @GetMapping("/reports")
//    public List<Object[]> getUserReports(
//            @RequestParam(required = false) Long userId,
//            @RequestParam(required = false) String firstname) {
//        return userService.getUserReports(userId, firstname);
//    }
 
}