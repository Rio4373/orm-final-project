package com.example.ormfinalproject.web;

import com.example.ormfinalproject.model.QuizSubmission;
import com.example.ormfinalproject.service.QuizService;
import com.example.ormfinalproject.web.dto.QuizAttemptRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/{quizId}/take")
    @ResponseStatus(HttpStatus.CREATED)
    public QuizSubmission takeQuiz(@PathVariable Long quizId,
                                   @Valid @RequestBody QuizAttemptRequest request) {
        return quizService.takeQuiz(quizId, request.studentId(), request.answers());
    }
}


