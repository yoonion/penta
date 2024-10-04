package com.task.penta.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {
    private final String username;
    private final String password;
}
