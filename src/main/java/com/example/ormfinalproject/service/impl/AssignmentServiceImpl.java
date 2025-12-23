package com.example.ormfinalproject.service.impl;

import com.example.ormfinalproject.model.Assignment;
import com.example.ormfinalproject.model.Lesson;
import com.example.ormfinalproject.model.Submission;
import com.example.ormfinalproject.model.User;
import com.example.ormfinalproject.repository.AssignmentRepository;
import com.example.ormfinalproject.repository.LessonRepository;
import com.example.ormfinalproject.repository.SubmissionRepository;
import com.example.ormfinalproject.repository.UserRepository;
import com.example.ormfinalproject.service.AssignmentService;
import com.example.ormfinalproject.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final LessonRepository lessonRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository,
                                 LessonRepository lessonRepository,
                                 SubmissionRepository submissionRepository,
                                 UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.lessonRepository = lessonRepository;
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Assignment createAssignment(Long lessonId, Assignment assignment) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson not found with id: " + lessonId));
        assignment.setLesson(lesson);
        return assignmentRepository.save(assignment);
    }

    @Override
    public Submission submitAssignment(Long assignmentId, Long studentId, String content) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found with id: " + assignmentId));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));

        return submissionRepository.findByAssignmentAndStudent(assignment, student)
                .orElseGet(() -> {
                    Submission submission = new Submission();
                    submission.setAssignment(assignment);
                    submission.setStudent(student);
                    submission.setSubmittedAt(LocalDateTime.now());
                    submission.setContent(content);
                    return submissionRepository.save(submission);
                });
    }

    @Override
    public Submission gradeSubmission(Long submissionId, Integer score, String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException("Submission not found with id: " + submissionId));
        submission.setScore(score);
        submission.setFeedback(feedback);
        return submission;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsForAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found with id: " + assignmentId));
        return submissionRepository.findByAssignment(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsForStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));
        return submissionRepository.findByStudent(student);
    }
}


