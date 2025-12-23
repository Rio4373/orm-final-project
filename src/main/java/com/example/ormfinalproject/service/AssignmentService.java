package com.example.ormfinalproject.service;

import com.example.ormfinalproject.model.Assignment;
import com.example.ormfinalproject.model.Submission;

import java.util.List;

public interface AssignmentService {

    Assignment createAssignment(Long lessonId, Assignment assignment);

    Submission submitAssignment(Long assignmentId, Long studentId, String content);

    Submission gradeSubmission(Long submissionId, Integer score, String feedback);

    List<Submission> getSubmissionsForAssignment(Long assignmentId);

    List<Submission> getSubmissionsForStudent(Long studentId);
}


