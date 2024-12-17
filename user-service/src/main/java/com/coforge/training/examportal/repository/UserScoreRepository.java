package com.coforge.training.examportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coforge.training.examportal.model.UserScore;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long>{

	/**
	 * Find all scores for a specific user by their userId.
	 */
	List<UserScore> findByUserId(Long userId);
	/**
	 * Find all scores for a specific topic.
	 */
	List<UserScore> findByTopic(String topic);

	/**
	 * Find scores for a specific user and topic combination.
	 */
	Optional<UserScore> findByUserIdAndTopic(Long userId, String topic);

	/**
	 * Find all scores sorted by topic for grouped result retrieval.
	 */
	@Query("SELECT u FROM UserScore u ORDER BY u.topic ASC")
	List<UserScore> findAllSortedByTopic();
}
