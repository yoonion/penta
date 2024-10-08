package com.task.penta.service;

import com.task.penta.dto.request.UserCreateRequestDto;
import com.task.penta.dto.response.UserCreateResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SystemUserServiceTest {

    @Autowired
    private SystemUserService systemUserService;

    private static final String CLIENT_IP = "127.0.0.1";

    @Test
    @DisplayName("일반 회원 생성 테스트")
    void createNormalUserTest() {

        // 일반 회원 생성
        UserCreateRequestDto requestDto =
                new UserCreateRequestDto(
                    "usertest",
                        "1234",
                        "테스트일반회원",
                        "user"
                );
        UserCreateResponseDto savedNormalUser = systemUserService.createUser(requestDto, CLIENT_IP);

        // 검증
        assertThat(savedNormalUser.getUserId()).isEqualTo("usertest");
        assertThat(savedNormalUser.getUserNm()).isEqualTo("테스트일반회원");
        assertThat(savedNormalUser.getUserAuth()).isEqualTo("SYSTEM_USER");
    }

    @Test
    @DisplayName("관리자 생성 테스트")
    void createAdminUserTest() {

        // admin 회원 생성
        UserCreateRequestDto requestDto =
                new UserCreateRequestDto(
                        "admintest",
                        "1234",
                        "테스트관리자",
                        "admin"
                );

        UserCreateResponseDto savedAdminUser = systemUserService.createUser(requestDto, CLIENT_IP);

        // 검증
        assertThat(savedAdminUser.getUserId()).isEqualTo("admintest");
        assertThat(savedAdminUser.getUserNm()).isEqualTo("테스트관리자");
        assertThat(savedAdminUser.getUserAuth()).isEqualTo("SYSTEM_ADMIN");
    }
}