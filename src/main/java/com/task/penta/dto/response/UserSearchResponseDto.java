package com.task.penta.dto.response;

import com.task.penta.entity.user.SystemUser;
import lombok.Getter;

@Getter
public class UserSearchResponseDto {
    private final String userId;
    private final String userNm;
    private final String userAuth;

    public UserSearchResponseDto(SystemUser systemUser) {
        this.userId = systemUser.getUserId();
        this.userNm = systemUser.getUserNm();
        this.userAuth = systemUser.getUserAuth();
    }
}
