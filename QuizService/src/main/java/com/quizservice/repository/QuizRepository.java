package com.quizservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizservice.model.Quiz;


public interface QuizRepository extends JpaRepository<Quiz, Integer>{

}
