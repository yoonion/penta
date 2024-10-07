package com.task.penta.entity.user;

import lombok.Getter;

@Getter
public enum SystemUserRoleEnum {
    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한

    private final String authority;

    SystemUserRoleEnum(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String ADMIN = "SYSTEM_ADMIN";
        public static final String USER = "SYSTEM_USER";
    }
}
