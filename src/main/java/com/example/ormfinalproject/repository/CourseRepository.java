package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Category;
import com.example.ormfinalproject.model.Course;
import com.example.ormfinalproject.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCategory(Category category);

    List<Course> findByTeacher(User teacher);

    @EntityGraph(attributePaths = {"modules", "modules.lessons"})
    List<Course> findWithStructureById(Long id);
}


