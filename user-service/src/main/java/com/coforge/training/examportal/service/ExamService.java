package com.coforge.training.examportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coforge.training.examportal.model.User;

/*
* Author      : Satyam.3.Singh
* Date        : 12:48:14 pm
* Time        : 12:48:14 pm
* Project     : user-service
*/

@Service
public class ExamService {
	
	 @Autowired
	    private UserService userService;
	 
	    public String submitExamResult(String username, boolean passedExam) {
	        User user = userService.findUser(username);
	 
	        if (user == null) {
	            return "User not found";
	        }
	 
	        if (passedExam) {
	            // If the user passes the exam, promote the user to the next level
	            if (user.getLevel() == 3) {
	                return "Congratulations, you have passed all levels!";
	            }
	            user.setLevel(user.getLevel() + 1);
	            user.setExamStatus(true);
	            userService.saveUser(user);  // Save the updated user
	            return "You have been promoted to level " + user.getLevel();
	        } else {
	            // If the user fails, reset their level and provide a logout option
	            user.setLevel(1);  // Reset to level 1
	            user.setExamStatus(false);
	            userService.saveUser(user);  // Save the updated user
	            return "You failed the exam. Please try again. [Logout button available]";
	        }
	    }
}
