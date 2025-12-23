package com.example.ormfinalproject.service;

import com.example.ormfinalproject.model.Enrollment;

import java.util.List;

public interface EnrollmentService {

    Enrollment enrollStudent(Long courseId, Long studentId);

    void unenrollStudent(Long courseId, Long studentId);

    List<Enrollment> getEnrollmentsForCourse(Long courseId);

    List<Enrollment> getEnrollmentsForStudent(Long studentId);
}


