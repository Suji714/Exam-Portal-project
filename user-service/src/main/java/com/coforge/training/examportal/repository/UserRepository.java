package com.coforge.training.examportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coforge.training.examportal.model.User;

import feign.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	
	 boolean existsByEmail(String email);
	 
	 boolean existsByMobile(String mobile);
	
	// Find users by name (case-insensitive)
	List<User> findByFirstnameContainingOrId(String firstname, Long id);

	// Find a user by ID (default JPA method, no need to define it explicitly)
	Optional<User> findById(Long id);

	   @Query("SELECT u.firstname, s.topic, s.score " +
			   "FROM User u JOIN UserScore s ON u.id = s.userId " +
			   "WHERE (:userId IS NULL OR u.id = :userId) " +
			              "AND (:firstname IS NULL OR u.firstname LIKE %:firstname%)")
			       List<Object[]> fetchUserReports(@Param("userId") Long userId,
			                                       @Param("firstname") String firstname);

}
