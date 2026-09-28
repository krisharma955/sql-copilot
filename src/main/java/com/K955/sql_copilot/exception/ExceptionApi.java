package com.K955.sql_copilot.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ExceptionApi(
        HttpStatus status,
        String message,
        Instant timestamp
) {
}
