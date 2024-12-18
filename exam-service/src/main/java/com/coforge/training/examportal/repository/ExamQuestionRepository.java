package com.coforge.training.examportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coforge.training.examportal.model.ExamQuestion;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
	
	List<ExamQuestion> findByTopic(String topic);
    @Query("SELECT COALESCE(MAX(q.questionId), 0) FROM ExamQuestion q WHERE q.topic = :topic")
    Long findMaxQuestionIdByTopic(@Param("topic") String topic);
    

}
