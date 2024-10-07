package com.task.penta.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @NotBlank(message = "수정할 회원의 이름을 입력해주세요.")
    private String userNm;
}
