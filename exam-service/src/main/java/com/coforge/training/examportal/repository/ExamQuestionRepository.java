package com.coforge.training.examportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coforge.training.examportal.model.ExamQuestion;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
	
	public List<ExamQuestion> findByExamTopic(String examTopic);
}
