package com.example.ormfinalproject.web;

import com.example.ormfinalproject.model.Assignment;
import com.example.ormfinalproject.model.Submission;
import com.example.ormfinalproject.service.AssignmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Validated
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/api/lessons/{lessonId}/assignments")
    @ResponseStatus(HttpStatus.CREATED)
    public Assignment createAssignment(@PathVariable Long lessonId,
                                       @Valid @RequestBody Assignment assignment) {
        return assignmentService.createAssignment(lessonId, assignment);
    }

    @PostMapping("/api/assignments/{assignmentId}/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public Submission submit(@PathVariable Long assignmentId,
                             @RequestParam @NotNull Long studentId,
                             @RequestParam @NotBlank String content) {
        return assignmentService.submitAssignment(assignmentId, studentId, content);
    }

    @PostMapping("/api/submissions/{submissionId}/grade")
    public Submission grade(@PathVariable Long submissionId,
                            @RequestParam @NotNull Integer score,
                            @RequestParam(required = false) String feedback) {
        return assignmentService.gradeSubmission(submissionId, score, feedback);
    }

    @GetMapping("/api/assignments/{assignmentId}/submissions")
    public List<Submission> getSubmissionsForAssignment(@PathVariable Long assignmentId) {
        return assignmentService.getSubmissionsForAssignment(assignmentId);
    }

    @GetMapping("/api/users/{studentId}/submissions")
    public List<Submission> getSubmissionsForStudent(@PathVariable Long studentId) {
        return assignmentService.getSubmissionsForStudent(studentId);
    }
}


