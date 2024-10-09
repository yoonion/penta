package com.task.penta.exception;

import com.task.penta.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 예외 전역 처리
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Interval exception
    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleExceptions(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.createFail(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), null));
    }

    // Bean validation exception
    @ExceptionHandler
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.createFail(ErrorCode.BAD_REQUEST.getMessage(), errors));
    }

    // CustomException 처리
    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.createFail(errorCode.getMessage(), null));
    }

    // 405 Method Not Allowed / 요청 가능한 http method 외 요청이 왔을 경우
    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.createFail(ErrorCode.HTTP_METHOD_NOT_ALLOWED.getMessage(), null));
    }
}
