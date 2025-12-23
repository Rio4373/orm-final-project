package com.example.ormfinalproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    private Profile profile;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Course> coursesTaught;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Submission> submissions;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<QuizSubmission> quizSubmissions;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<CourseReview> courseReviews;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();
}


