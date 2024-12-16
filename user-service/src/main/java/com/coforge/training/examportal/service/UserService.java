package com.coforge.training.examportal.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private UserRepository userRepo;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private ExamServiceFeignClient examServiceFeignClient;

	@Autowired
	private UserScoreRepository userScoreRepository;

	//Di using conatructor
	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user); //save() pre-defined method in JPA repo
	}


	//	public int calculateAndStoreScore(Long userId, String topic, List<AnswerRequest> answers) {
	//		// Fetch questions for the topic
	//		List<ExamQuestion> questions = examServiceFeignClient.questionByExamTopic(topic);
	//
	//		// Map question IDs to correct options
	//		Map<Long, String> correctOptions = questions.stream().collect(Collectors.toMap(
	//				ExamQuestion::getId,
	//				question -> {
	//					if (question.getCorrectAnswer().equalsIgnoreCase(question.getOptionA())) return "A";
	//					if (question.getCorrectAnswer().equalsIgnoreCase(question.getOptionB())) return "B";
	//					if (question.getCorrectAnswer().equalsIgnoreCase(question.getOptionC())) return "C";
	//					if (question.getCorrectAnswer().equalsIgnoreCase(question.getOptionD())) return "D";
	//					return null;
	//				}
	//				));
	//
	//		// Calculate the score
	//		int totalScore = 0;
	//		for (AnswerRequest answer : answers) {
	//			if (correctOptions.containsKey(answer.getQuestionId()) &&
	//					((String) correctOptions.get(answer.getQuestionId())).equalsIgnoreCase(answer.getSelectedOption())) {
	//				totalScore++;
	//			}
	//		}
	//
	//		// Save the score in the database
	//		Optional<UserScore> existingScore = userScoreRepository.findByUserIdAndTopic(userId, topic);
	//		UserScore userScore;
	//		if (existingScore.isPresent()) {
	//			userScore = existingScore.get();
	//			userScore.setScore(totalScore);
	//		} else {
	//			userScore = new UserScore();
	//			userScore.setUserId(userId);
	//			userScore.setTopic(topic);
	//			userScore.setScore(totalScore);
	//		}
	//		userScoreRepository.save(userScore);
	//
	//		return totalScore;
	//	}
	//	
	//	public List<UserScore> getUserScores(Long userId){
	//		return userScoreRepository.findByUserId(userId);
	//	}

	/**
	 * Fetch questions by topic, validate answers, calculate the score, and store it.
	 */
	public int calculateAndStoreScore(Long userId, String topic, List<AnswerRequest> answers) {
		// Fetch all questions for the topic from the ExamService
		List<ExamQuestion> questions = examServiceFeignClient.questionByExamTopic(topic);

		// Map each question ID to the correct option (A, B, C, D)
		Map<Long, String> correctOptions = questions.stream().collect(Collectors.toMap(
				ExamQuestion::getId,
				question -> {
					if (question.getCorrectAnswer().equalsIgnoreCase(question.getOptionA())) return "A";
					if (question.getCorrectAnswer().equalsIgnoreCase(question.getOptionB())) return "B";
					if (question.getCorrectAnswer().equalsIgnoreCase(question.getOptionC())) return "C";
					if (question.getCorrectAnswer().equalsIgnoreCase(question.getOptionD())) return "D";
					return null;
				}
				));

		// Calculate the user's score based on their answers
		int totalScore = 0;
		for (AnswerRequest answer : answers) {
			if (correctOptions.containsKey(answer.getQuestionId()) &&
					correctOptions.get(answer.getQuestionId()).equalsIgnoreCase(answer.getSelectedOption())) {
				totalScore++; // Increment score for correct answer
			}
		}

		// Save the user's score for this topic
		saveUserScore(userId, topic, totalScore);

		return totalScore;
	}

	/**
	 * Save or update the user's score for a specific topic.
	 */
	private void saveUserScore(Long userId, String topic, int score) {
		Optional<UserScore> existingScore = userScoreRepository.findByUserIdAndTopic(userId, topic);
		UserScore userScore;
		if (existingScore.isPresent()) {
			userScore = existingScore.get();
			userScore.setScore(score); // Update existing score
		} else {
			userScore = new UserScore();
			userScore.setUserId(userId);
			userScore.setTopic(topic);
			userScore.setScore(score); // Create new score entry
		}
		userScoreRepository.save(userScore);
	}

	/**
	 * Retrieve all scores for a user.
	 */
	public List<UserScore> getUserScores(Long userId) {
		return userScoreRepository.findByUserId(userId);
	}
}
