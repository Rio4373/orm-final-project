package com.example.ormfinalproject.service;

import com.example.ormfinalproject.model.QuizSubmission;

import java.util.Map;

public interface QuizService {

    QuizSubmission takeQuiz(Long quizId, Long studentId, Map<Long, Long> answersByQuestionId);
}


