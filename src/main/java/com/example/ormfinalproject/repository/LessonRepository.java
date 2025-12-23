package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Lesson;
import com.example.ormfinalproject.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByModule(Module module);
}


