package com.task.penta.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "회원 아이디를 입력해주세요.")
    private final String userId;

    @NotBlank(message = "회원 비밀번호를 입력해주세요.")
    private final String userPw;

    @NotBlank(message = "회원 이름을 입력해주세요.")
    private final String userNm;
}
