package com.task.penta.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemUserCreateRequestDto {
    private final String userId;
    private final String userPw;
    private final String userNm;
    private final String userAuth;
}
