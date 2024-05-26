package com.quizservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizservice.feign.QuizInterface;
import com.quizservice.model.QuestionWrapper;
import com.quizservice.model.Quiz;
import com.quizservice.model.Response;
import com.quizservice.repository.QuizRepository;

@Service
public class QuizService {
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuizInterface quizInterface;
	
	public ResponseEntity<String> creatQuiz(String category, int numQ) {
		List<Integer>questions=quizInterface.getQuestionsForQuiz(category, numQ).getBody();
		Quiz quiz=new Quiz();
		quiz.setQuestions(questions);
		quizRepository.save(quiz);
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
	}
	
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
	    Quiz quiz = quizRepository.findById(id).get();
//	    List<Integer>questionIds=quiz.getQuestionIds();
	    List<Integer>questionIds=quiz.getQuestions();

	    ResponseEntity<List<QuestionWrapper>>questions=quizInterface.getQuestionsFromId(questionIds);

	    return questions;
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
		ResponseEntity<Integer> score=quizInterface.getScore(response);
		return score;
	}
}
