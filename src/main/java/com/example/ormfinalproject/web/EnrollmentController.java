package com.example.ormfinalproject.web;

import com.example.ormfinalproject.model.Enrollment;
import com.example.ormfinalproject.service.EnrollmentService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/enrollments")
@Validated
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Enrollment enroll(@PathVariable Long courseId,
                             @RequestParam @NotNull Long studentId) {
        return enrollmentService.enrollStudent(courseId, studentId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unenroll(@PathVariable Long courseId,
                         @RequestParam @NotNull Long studentId) {
        enrollmentService.unenrollStudent(courseId, studentId);
    }

    @GetMapping
    public List<Enrollment> getEnrollmentsForCourse(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsForCourse(courseId);
    }
}


