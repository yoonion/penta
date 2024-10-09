package com.task.penta.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @NotBlank(message = "수정할 회원의 이름을 입력해주세요.")
    @Size(max = 50, message = "회원 이름은 50자 이하로 입력해주세요.")
    private String userNm;
}
