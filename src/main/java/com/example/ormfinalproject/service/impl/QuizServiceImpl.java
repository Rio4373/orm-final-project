package com.example.ormfinalproject.service.impl;

import com.example.ormfinalproject.model.*;
import com.example.ormfinalproject.repository.*;
import com.example.ormfinalproject.service.QuizService;
import com.example.ormfinalproject.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final UserRepository userRepository;

    public QuizServiceImpl(QuizRepository quizRepository,
                           QuestionRepository questionRepository,
                           AnswerOptionRepository answerOptionRepository,
                           QuizSubmissionRepository quizSubmissionRepository,
                           UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
        this.quizSubmissionRepository = quizSubmissionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public QuizSubmission takeQuiz(Long quizId, Long studentId, Map<Long, Long> answersByQuestionId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundException("Quiz not found with id: " + quizId));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));

        int totalQuestions = quiz.getQuestions().size();
        int correctAnswers = 0;

        for (Question question : quiz.getQuestions()) {
            Long selectedOptionId = answersByQuestionId.get(question.getId());
            if (selectedOptionId == null) {
                continue;
            }
            AnswerOption selected = answerOptionRepository.findById(selectedOptionId)
                    .orElseThrow(() -> new NotFoundException("Answer option not found with id: " + selectedOptionId));
            if (!selected.getQuestion().getId().equals(question.getId())) {
                continue;
            }
            if (selected.isCorrect()) {
                correctAnswers++;
            }
        }

        int score = totalQuestions == 0 ? 0 : (int) Math.round((correctAnswers * 100.0) / totalQuestions);

        QuizSubmission submission = quizSubmissionRepository.findByQuizAndStudent(quiz, student)
                .orElseGet(QuizSubmission::new);
        submission.setQuiz(quiz);
        submission.setStudent(student);
        submission.setScore(score);
        submission.setTakenAt(LocalDateTime.now());

        return quizSubmissionRepository.save(submission);
    }
}


