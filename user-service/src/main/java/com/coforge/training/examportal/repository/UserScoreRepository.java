package com.coforge.training.examportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coforge.training.examportal.model.UserScore;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long>{
	
	 Optional<UserScore> findByUserIdAndTopic(Long userId, String topic);
	 
	 List<UserScore> findByUserId(Long userId);
}
