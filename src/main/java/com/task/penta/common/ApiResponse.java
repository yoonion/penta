package com.task.penta.common;

import lombok.Getter;

import java.util.Map;

@Getter
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";

    private final String status;
    private final String message;
    private final T data;
    private final Map<String, String> errors;

    private ApiResponse(String status, String message, T data, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, null, data, null);
    }

    // 예외 발생으로 API 호출 실패 시
    public static ApiResponse<?> createFail(String message, Map<String, String> errors) {
        return new ApiResponse<>(FAIL_STATUS, message, null, errors);
    }
}
