package com.task.penta.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemUserUpdateRequestDto {

    @NotBlank(message = "수정할 회원의 아이디를 입력해주세요.")
    private final String userId;

    @NotBlank(message = "수정할 회원의 이름을 입력해주세요.")
    private final String userNm;
}
