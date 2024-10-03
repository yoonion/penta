package com.task.penta.exception;

import com.task.penta.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 예외 전역 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bean validation exception
    @ExceptionHandler
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createFail(e.getBindingResult()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleIllegalArgumentExceptions(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createError(e.getMessage()));
    }

    // Interval exception
    @ExceptionHandler
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.createError(e.getMessage()));
    }
}
