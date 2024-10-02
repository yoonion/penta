package com.task.penta.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemUserUpdateResponseDto {
    private final String userId;
    private final String userNm;
}
