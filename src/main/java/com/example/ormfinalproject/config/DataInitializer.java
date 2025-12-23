package com.example.ormfinalproject.config;

import com.example.ormfinalproject.model.AnswerOption;
import com.example.ormfinalproject.model.Assignment;
import com.example.ormfinalproject.model.Category;
import com.example.ormfinalproject.model.Course;
import com.example.ormfinalproject.model.Enrollment;
import com.example.ormfinalproject.model.EnrollmentStatus;
import com.example.ormfinalproject.model.Lesson;
import com.example.ormfinalproject.model.Module;
import com.example.ormfinalproject.model.Question;
import com.example.ormfinalproject.model.QuestionType;
import com.example.ormfinalproject.model.Quiz;
import com.example.ormfinalproject.model.QuizSubmission;
import com.example.ormfinalproject.model.Submission;
import com.example.ormfinalproject.model.User;
import com.example.ormfinalproject.model.UserRole;
import com.example.ormfinalproject.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               CategoryRepository categoryRepository,
                               CourseRepository courseRepository,
                               ModuleRepository moduleRepository,
                               LessonRepository lessonRepository,
                               AssignmentRepository assignmentRepository,
                               QuizRepository quizRepository,
                               QuestionRepository questionRepository,
                               AnswerOptionRepository answerOptionRepository,
                               EnrollmentRepository enrollmentRepository,
                               SubmissionRepository submissionRepository,
                               QuizSubmissionRepository quizSubmissionRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            User teacher = new User();
            teacher.setName("Teacher One");
            teacher.setEmail("teacher1@example.com");
            teacher.setRole(UserRole.TEACHER);

            User student = new User();
            student.setName("Student One");
            student.setEmail("student1@example.com");
            student.setRole(UserRole.STUDENT);

            userRepository.saveAll(List.of(teacher, student));

            Category category = new Category();
            category.setName("Programming");
            categoryRepository.save(category);

            Course course = new Course();
            course.setTitle("Основы Hibernate");
            course.setDescription("Учебный курс по Hibernate и JPA");
            course.setCategory(category);
            course.setTeacher(teacher);
            course.setDurationInHours(10);
            course.setStartDate(LocalDate.now());
            courseRepository.save(course);

            Module module = new Module();
            module.setCourse(course);
            module.setTitle("Модуль 1: Введение");
            module.setOrderIndex(1);
            moduleRepository.save(module);

            Lesson lesson = new Lesson();
            lesson.setModule(module);
            lesson.setTitle("Урок 1: Основы ORM");
            lesson.setContent("Текст урока об основах ORM и Hibernate");
            lessonRepository.save(lesson);

            Assignment assignment = new Assignment();
            assignment.setLesson(lesson);
            assignment.setTitle("Домашнее задание 1");
            assignment.setDescription("Ответить на вопросы по теме ORM");
            assignment.setDueDate(LocalDateTime.now().plusDays(7));
            assignment.setMaxScore(100);
            assignmentRepository.save(assignment);

            Quiz quiz = new Quiz();
            quiz.setModule(module);
            quiz.setTitle("Тест по модулю 1");
            quiz.setTimeLimitMinutes(15);
            quizRepository.save(quiz);

            Question question = new Question();
            question.setQuiz(quiz);
            question.setText("Что такое ORM?");
            question.setType(QuestionType.SINGLE_CHOICE);
            questionRepository.save(question);

            AnswerOption opt1 = new AnswerOption();
            opt1.setQuestion(question);
            opt1.setText("Object-Relational Mapping");
            opt1.setCorrect(true);

            AnswerOption opt2 = new AnswerOption();
            opt2.setQuestion(question);
            opt2.setText("Online Resource Manager");
            opt2.setCorrect(false);

            answerOptionRepository.saveAll(List.of(opt1, opt2));

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrolledAt(LocalDateTime.now());
            enrollment.setStatus(EnrollmentStatus.ACTIVE);
            enrollmentRepository.save(enrollment);

            Submission submission = new Submission();
            submission.setAssignment(assignment);
            submission.setStudent(student);
            submission.setSubmittedAt(LocalDateTime.now());
            submission.setContent("Мой ответ на задание");
            submission.setScore(90);
            submission.setFeedback("Хорошая работа");
            submissionRepository.save(submission);

            QuizSubmission quizSubmission = new QuizSubmission();
            quizSubmission.setQuiz(quiz);
            quizSubmission.setStudent(student);
            quizSubmission.setScore(100);
            quizSubmission.setTakenAt(LocalDateTime.now());
            quizSubmissionRepository.save(quizSubmission);
        };
    }
}


