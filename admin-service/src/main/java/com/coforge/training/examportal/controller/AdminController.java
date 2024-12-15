package com.coforge.training.examportal.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.examportal.model.Question;
import com.coforge.training.examportal.service.QuestionService;
import com.coforge.training.examportal.service.UserServiceClient;

import com.coforge.training.examportal.model.User;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private QuestionService questionService;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    private static final String ADMIN_USERNAME = "admin";
//    private static final String ADMIN_PASSWORD = "admin123";

//    @PostMapping("/login")
//    public ResponseEntity<?> adminLogin(@RequestParam String username, @RequestParam String password) {
//        if (ADMIN_USERNAME.equals(username) && passwordEncoder.matches(password, ADMIN_PASSWORD)) {
//            return ResponseEntity.ok("Admin logged in successfully");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin credentials");
//    }

    @PutMapping("/update-question")
    public ResponseEntity<?> updateQuestion(@RequestParam Long questionId, @RequestBody Question updatedQuestion) {
        Question question = questionService.getQuestionById(questionId);

        if (question == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }

        question.setQuestionText(updatedQuestion.getQuestionText());
        question.setAnswer(updatedQuestion.getAnswer());

        questionService.saveQuestion(question);
        return ResponseEntity.ok("Question updated successfully");
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable String username) {
       ResponseEntity<?> responseEntity = userServiceClient.findByUsername(username);

      if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
       }

      User user = (User) responseEntity.getBody();
        return ResponseEntity.ok(user);
    }

//    @PutMapping("/promote-user/{username}")
//    public ResponseEntity<?> promoteUser(@PathVariable String username) {
//        ResponseEntity<?> responseEntity = userServiceClient.findByUsername(username);
//
//        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
////        User user = responseEntity.getBody();
////
////        if (user.getLevel() == 3) {
////            return ResponseEntity.ok("User has already completed all levels. Congratulations!");
////        }
////
////        user.setLevel(user.getLevel() + 1);
////        userServiceClient.saveUser(user);  // Assuming you have this method
////
//        return ResponseEntity.ok("User promoted to level " + user.getLevel());
//    }
}
