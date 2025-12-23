package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Module;
import com.example.ormfinalproject.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByModule(Module module);
}


