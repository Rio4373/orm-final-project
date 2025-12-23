package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.AnswerOption;
import com.example.ormfinalproject.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {

    List<AnswerOption> findByQuestion(Question question);
}


