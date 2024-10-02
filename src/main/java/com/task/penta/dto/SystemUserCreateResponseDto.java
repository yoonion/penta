package com.task.penta.dto;

import com.task.penta.entity.SystemUser;
import lombok.Getter;

@Getter
public class SystemUserCreateResponseDto {
    private final String userId;
    private final String userNm;
    private final String userAuth;

    public SystemUserCreateResponseDto(SystemUser systemUser) {
        this.userId = systemUser.getUserId();
        this.userNm = systemUser.getUserNm();
        this.userAuth = systemUser.getUserAuth();
    }
}
