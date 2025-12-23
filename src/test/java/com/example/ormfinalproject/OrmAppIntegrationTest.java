package com.example.ormfinalproject;

import com.example.ormfinalproject.model.*;
import com.example.ormfinalproject.repository.*;
import com.example.ormfinalproject.service.AssignmentService;
import com.example.ormfinalproject.service.EnrollmentService;
import com.example.ormfinalproject.service.QuizService;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
class OrmAppIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("orm_final_project_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Test
    void contextLoadsAndSeedsData() {
        List<User> users = userRepository.findAll();
        List<Course> courses = courseRepository.findAll();
        assertFalse(users.isEmpty(), "Users should be preloaded");
        assertFalse(courses.isEmpty(), "Courses should be preloaded");
    }

    @Test
    void enrollAndUnenrollStudent() {
        User student = userRepository.findAll().stream()
                .filter(u -> u.getRole() == UserRole.STUDENT)
                .findFirst()
                .orElseThrow();
        Course course = courseRepository.findAll().get(0);

        var enrollment = enrollmentService.enrollStudent(course.getId(), student.getId());
        assertNotNull(enrollment.getId());

        var enrollments = enrollmentService.getEnrollmentsForCourse(course.getId());
        assertTrue(enrollments.stream().anyMatch(e -> e.getStudent().getId().equals(student.getId())));

        enrollmentService.unenrollStudent(course.getId(), student.getId());
        var afterUnenroll = enrollmentService.getEnrollmentsForCourse(course.getId());
        assertTrue(afterUnenroll.stream().noneMatch(e -> e.getStudent().getId().equals(student.getId())));
    }

    @Test
    void submitAndGradeAssignment() {
        User student = userRepository.findAll().stream()
                .filter(u -> u.getRole() == UserRole.STUDENT)
                .findFirst()
                .orElseThrow();

        Lesson lesson = lessonRepository.findAll().get(0);

        Assignment newAssignment = new Assignment();
        newAssignment.setTitle("Интеграционный тест по ДЗ");
        newAssignment.setDescription("Проверка submit/grade");
        newAssignment.setMaxScore(50);

        Assignment assignment = assignmentService.createAssignment(lesson.getId(), newAssignment);
        assertNotNull(assignment.getId());

        var submission = assignmentService.submitAssignment(assignment.getId(), student.getId(), "Ответ студента");
        assertNotNull(submission.getId());
        assertEquals("Ответ студента", submission.getContent());

        var graded = assignmentService.gradeSubmission(submission.getId(), 45, "Хорошо");
        assertEquals(45, graded.getScore());
        assertEquals("Хорошо", graded.getFeedback());
    }

    @Test
    void takeQuizAndStoreResult() {
        User student = userRepository.findAll().stream()
                .filter(u -> u.getRole() == UserRole.STUDENT)
                .findFirst()
                .orElseThrow();

        Quiz quiz = quizRepository.findAll().get(0);
        Question question = questionRepository.findByQuiz(quiz).get(0);
        AnswerOption correct = answerOptionRepository.findByQuestion(question).stream()
                .filter(AnswerOption::isCorrect)
                .findFirst()
                .orElseThrow();

        var submission = quizService.takeQuiz(quiz.getId(), student.getId(),
                Map.of(question.getId(), correct.getId()));

        assertNotNull(submission.getId());
        assertEquals(100, submission.getScore());
    }

    @Test
    void lazyInitializationExceptionWhenAccessingModulesOutsideSession() {
        Course course = courseRepository.findAll().get(0);
        assertThrows(LazyInitializationException.class, () -> course.getModules().size());
    }
}


