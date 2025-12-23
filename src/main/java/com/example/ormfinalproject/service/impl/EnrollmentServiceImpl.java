package com.example.ormfinalproject.service.impl;

import com.example.ormfinalproject.model.Course;
import com.example.ormfinalproject.model.Enrollment;
import com.example.ormfinalproject.model.EnrollmentStatus;
import com.example.ormfinalproject.model.User;
import com.example.ormfinalproject.repository.CourseRepository;
import com.example.ormfinalproject.repository.EnrollmentRepository;
import com.example.ormfinalproject.repository.UserRepository;
import com.example.ormfinalproject.service.EnrollmentService;
import com.example.ormfinalproject.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 UserRepository userRepository,
                                 CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Enrollment enrollStudent(Long courseId, Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));

        return enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseGet(() -> {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setStudent(student);
                    enrollment.setCourse(course);
                    enrollment.setEnrolledAt(LocalDateTime.now());
                    enrollment.setStatus(EnrollmentStatus.ACTIVE);
                    return enrollmentRepository.save(enrollment);
                });
    }

    @Override
    public void unenrollStudent(Long courseId, Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));

        enrollmentRepository.findByStudentAndCourse(student, course)
                .ifPresent(enrollmentRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));
        return enrollmentRepository.findByCourse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsForStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));
        return enrollmentRepository.findByStudent(student);
    }
}


