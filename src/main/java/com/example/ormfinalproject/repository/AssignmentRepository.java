package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Assignment;
import com.example.ormfinalproject.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByLesson(Lesson lesson);
}


