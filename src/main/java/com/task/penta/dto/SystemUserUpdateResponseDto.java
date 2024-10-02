package com.task.penta.dto;

import com.task.penta.entity.SystemUser;
import lombok.Getter;

@Getter
public class SystemUserUpdateResponseDto {
    private final String userId;
    private final String userNm;

    public SystemUserUpdateResponseDto(SystemUser user) {
        this.userId = user.getUserId();
        this.userNm = user.getUserNm();
    }
}
