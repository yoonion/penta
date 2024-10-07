package com.task.penta.dto.response;

import com.task.penta.entity.user.SystemUser;
import lombok.Getter;

@Getter
public class UserUpdateResponseDto {
    private final String userId;
    private final String userNm;

    public UserUpdateResponseDto(SystemUser user) {
        this.userId = user.getUserId();
        this.userNm = user.getUserNm();
    }
}
