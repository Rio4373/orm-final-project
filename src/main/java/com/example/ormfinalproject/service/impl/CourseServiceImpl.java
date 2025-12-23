package com.example.ormfinalproject.service.impl;

import com.example.ormfinalproject.model.Course;
import com.example.ormfinalproject.repository.CourseRepository;
import com.example.ormfinalproject.service.CourseService;
import com.example.ormfinalproject.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course updated) {
        Course existing = getCourse(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setDurationInHours(updated.getDurationInHours());
        existing.setStartDate(updated.getStartDate());
        existing.setCategory(updated.getCategory());
        existing.setTeacher(updated.getTeacher());
        return existing;
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}


