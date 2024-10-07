package com.task.penta.dto.response;

import com.task.penta.entity.user.SystemUser;
import lombok.Getter;

@Getter
public class UserCreateResponseDto {
    private final String userId;
    private final String userNm;
    private final String userAuth;

    public UserCreateResponseDto(SystemUser systemUser) {
        this.userId = systemUser.getUserId();
        this.userNm = systemUser.getUserNm();
        this.userAuth = systemUser.getUserAuth();
    }
}
