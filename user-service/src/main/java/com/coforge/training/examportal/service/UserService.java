package com.coforge.training.examportal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coforge.training.examportal.model.AnswerRequest;
import com.coforge.training.examportal.model.ExamQuestion;
import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.model.UserScore;
import com.coforge.training.examportal.repository.UserRepository;
import com.coforge.training.examportal.repository.UserScoreRepository;

@Service
public class UserService {

    @Autowired
    private ExamServiceFeignClient examServiceFeignClient;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserScoreRepository userScoreRepository;

    @Autowired
    private UserRepository userRepository;

    public UserService(ExamServiceFeignClient examServiceFeignClient, PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.examServiceFeignClient = examServiceFeignClient;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // to save the user details with password encryption
    public void saveUser(User user) throws Exception {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
        }
        if (userRepository.existsByMobile(user.getMobile())) {
            throw new Exception("Mobile number already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Validate user answers, calculate score, and store it.
     */
    public int calculateAndStoreScore(Long userId, String topic, List<AnswerRequest> answers) {
        List<ExamQuestion> questions = examServiceFeignClient.getQuestionsByTopic(topic);

        // Map question IDs to correct options
        Map<Long, String> correctOptions = questions.stream()
                .collect(Collectors.toMap(ExamQuestion::getQuestionId, ExamQuestion::getCorrectAnswer));

        int totalScore = 0;
        for (AnswerRequest answer : answers) {
            if (correctOptions.containsKey(answer.getQuestionId()) &&
                    correctOptions.get(answer.getQuestionId()).equalsIgnoreCase(answer.getSelectedOption())) {
                totalScore++; // Increment score for correct answers
            }
        }

        saveUserScore(userId, topic, totalScore);
        return totalScore;
    }

    /**
     * Save or update user score.
     */
    private void saveUserScore(Long userId, String topic, int score) {
        Optional<UserScore> existingScore = userScoreRepository.findByUserIdAndTopic(userId, topic);
        UserScore userScore;
        if (existingScore.isPresent()) {
            userScore = existingScore.get();
            userScore.setScore(score);
        } else {
            userScore = new UserScore();
            userScore.setUserId(userId);
            userScore.setTopic(topic);
            userScore.setScore(score);
        }
        userScoreRepository.save(userScore);
    }

    // for admin to fetch all user details with marks
    public List<Object[]> getUserReports(Long userId, String firstname) {
        return userRepository.fetchUserReports(userId, firstname);
    }

    /**
     * Retrieve all scores for a user across all topics.
     */
    public List<UserScore> getUserScores(Long userId) {
        return userScoreRepository.findByUserId(userId);
    }
}
