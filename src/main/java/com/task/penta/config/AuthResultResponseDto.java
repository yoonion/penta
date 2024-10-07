package com.task.penta.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResultResponseDto {
    private final int status;
    private final String message;
}
