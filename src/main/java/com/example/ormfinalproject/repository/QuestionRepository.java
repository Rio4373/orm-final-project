package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Question;
import com.example.ormfinalproject.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuiz(Quiz quiz);
}


