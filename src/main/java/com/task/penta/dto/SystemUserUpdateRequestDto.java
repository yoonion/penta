package com.task.penta.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemUserUpdateRequestDto {

    @NotBlank
    private final String userId;

    @NotBlank
    private final String userNm;
}
