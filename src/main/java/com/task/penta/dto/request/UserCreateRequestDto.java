package com.task.penta.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "회원 아이디를 입력해주세요.")
    @Size(min = 3, max = 20, message = "회원 아이디는 3자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "회원 아이디는 영문과 숫자만 포함해야 합니다.")
    private final String userId;

    @NotBlank(message = "회원 비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "회원 비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$", message = "회원 비밀번호는 최소 8자 이상이고, 숫자와 영문자를 모두 포함해야 합니다.")
    private final String userPw;

    @NotBlank(message = "회원 이름을 입력해주세요.")
    @Size(max = 50, message = "회원 이름은 50자 이하로 입력해주세요.")
    private final String userNm;

    @NotBlank(message = "회원 권한을 입력해주세요.")
    private final String userAuth;
}
