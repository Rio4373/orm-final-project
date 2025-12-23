package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Course;
import com.example.ormfinalproject.model.CourseReview;
import com.example.ormfinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {

    List<CourseReview> findByCourse(Course course);

    List<CourseReview> findByStudent(User student);

    Optional<CourseReview> findByCourseAndStudent(Course course, User student);
}


