package com.task.penta.exception;

import com.task.penta.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 예외 전역 처리
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bean validation exception
    @ExceptionHandler
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createFail(e.getBindingResult()));
    }

    // CustomException 처리
    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        log.info("-- CustomExceptionHandler -- ");
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.createError(errorCode.getMessage()));
    }

    // Interval exception
    @ExceptionHandler
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.createError(e.getMessage()));
    }
}
