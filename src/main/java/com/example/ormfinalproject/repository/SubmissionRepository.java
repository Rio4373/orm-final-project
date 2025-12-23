package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Assignment;
import com.example.ormfinalproject.model.Submission;
import com.example.ormfinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByAssignment(Assignment assignment);

    List<Submission> findByStudent(User student);

    Optional<Submission> findByAssignmentAndStudent(Assignment assignment, User student);
}


