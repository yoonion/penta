package com.task.penta.exception;

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
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(fieldError.getDefaultMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleIllegalArgumentExceptions(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    // Interval exception
    @ExceptionHandler
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
