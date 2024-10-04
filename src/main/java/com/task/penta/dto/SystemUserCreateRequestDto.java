package com.task.penta.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemUserCreateRequestDto {

    @NotBlank(message = "회원 아이디를 입력해주세요.")
    private final String userId;

    @NotBlank(message = "회원 비밀번호를 입력해주세요.")
    private final String userPw;

    @NotBlank(message = "회원 이름을 입력해주세요.")
    private final String userNm;

    // 일반 회원만 가입이 가능하도록 SystemUserService 코드에서 지정해준다.
    /*@NotBlank(message = "회원 권한을 입력해주세요.")
    private final String userAuth;*/
}
