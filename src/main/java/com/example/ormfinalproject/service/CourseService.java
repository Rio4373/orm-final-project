package com.example.ormfinalproject.service;

import com.example.ormfinalproject.model.Course;

import java.util.List;

public interface CourseService {

    Course createCourse(Course course);

    Course updateCourse(Long id, Course updated);

    void deleteCourse(Long id);

    Course getCourse(Long id);

    List<Course> getAllCourses();
}


