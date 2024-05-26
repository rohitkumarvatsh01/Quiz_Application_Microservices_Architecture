package com.questionService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.questionService.model.Question;
import com.questionService.model.QuestionWrapper;
import com.questionService.model.Response;
import com.questionService.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	public ResponseEntity<List<Question>> getAllQuestion() {
		try {
			return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
		try {
			return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<String> addQuestion(Question question) {
		try {
			questionRepository.save(question);
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<>("An unexpected error occurred: ", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
		List<Integer>questions=questionRepository.findRandomQuestionsByCategory(categoryName, numQuestions);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> QuestionWrapper(List<Integer> questionIds) {
		List<QuestionWrapper>wrappers=new ArrayList<>();
		List<Question>questions=new ArrayList<>();
		
		for(Integer id:questionIds) {
			questions.add(questionRepository.findById(id).get()); 
		}
		
		for(Question question:questions) {
			QuestionWrapper wrapper=new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			wrappers.add(wrapper);
		}
		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
	    int right = 0;
	    for (Response response : responses) {
	        Optional<Question> optionalQuestion = questionRepository.findById(response.getId());
	        if (optionalQuestion.isPresent()) {
	            Question question = optionalQuestion.get();
	            if (response.getResponse().equals(question.getRightAnswer())) {
	                right++;
	            }
	        } else {
	            System.err.println("Question with id " + response.getId() + " not found.");
	        }
	    }
	    return new ResponseEntity<>(right, HttpStatus.OK);
	}
}
