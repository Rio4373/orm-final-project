package com.example.ormfinalproject.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record QuizAttemptRequest(
        @NotNull Long studentId,
        @NotEmpty Map<Long, Long> answers
) {
}


