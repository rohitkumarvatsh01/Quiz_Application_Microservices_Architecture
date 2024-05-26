package com.questionService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.questionService.model.Question;
import com.questionService.model.QuestionWrapper;
import com.questionService.model.Response;
import com.questionService.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/allQuestions")
	public ResponseEntity<List<Question>> getAllQuestion() {
		return questionService.getAllQuestion();
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
		return questionService.getQuestionByCategory(category);
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}
	
	@GetMapping("/generate")
	public ResponseEntity<List<Integer>>getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions ){
		return questionService.getQuestionsForQuiz(categoryName, numQuestions);
	}
	
	@PostMapping("/getQuestions")
	public ResponseEntity<List<QuestionWrapper>>getQuestionsFromId(@RequestBody List<Integer>questionIds){
		System.out.println(environment.getProperty("local.server.port"));
		return questionService.QuestionWrapper(questionIds);
	}
	
	@GetMapping("/getScore")
	public ResponseEntity<Integer>getScore(@RequestBody List<Response>responses){
		return questionService.getScore(responses);
	}
}
