package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Quiz;
import com.example.ormfinalproject.model.QuizSubmission;
import com.example.ormfinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {

    List<QuizSubmission> findByStudent(User student);

    List<QuizSubmission> findByQuiz(Quiz quiz);

    Optional<QuizSubmission> findByQuizAndStudent(Quiz quiz, User student);
}


