package com.task.penta.service;

import com.task.penta.dto.request.UserCreateRequestDto;
import com.task.penta.dto.response.UserCreateResponseDto;
import com.task.penta.exception.CustomException;
import org.assertj.core.api.Assertions;
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
    private static final String REQUEST_URL = "http://example.com";

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
        UserCreateResponseDto savedNormalUser = systemUserService.createUser(requestDto, CLIENT_IP, REQUEST_URL);

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

        UserCreateResponseDto savedAdminUser = systemUserService.createUser(requestDto, CLIENT_IP, REQUEST_URL);

        // 검증
        assertThat(savedAdminUser.getUserId()).isEqualTo("admintest");
        assertThat(savedAdminUser.getUserNm()).isEqualTo("테스트관리자");
        assertThat(savedAdminUser.getUserAuth()).isEqualTo("SYSTEM_ADMIN");
    }

    @Test
    @DisplayName("ID 중복 가입 시 DuplicateUserException 예외가 발생해야 한다")
    void duplicateUserTest() {
        // 일반 회원 생성
        UserCreateRequestDto requestDto1 =
                new UserCreateRequestDto(
                        "usertest55",
                        "1234",
                        "테스트일반회원",
                        "user"
                );

        UserCreateResponseDto savedNormalUser1 = systemUserService.createUser(requestDto1, CLIENT_IP, REQUEST_URL);
        // 첫 번째 생성이 성공했는지 검증
        assertThat(savedNormalUser1).isNotNull();

        // 중복 회원 생성
        UserCreateRequestDto requestDto2 =
                new UserCreateRequestDto(
                        "usertest55",
                        "1234",
                        "테스트일반회원",
                        "user"
                );

        // AssertJ를 사용하여 예외 발생 여부 검증
        Assertions.assertThatThrownBy(() -> {
            systemUserService.createUser(requestDto2, CLIENT_IP, REQUEST_URL);
        })
                .isInstanceOf(CustomException.class)
                .hasMessage("중복된 회원 ID 입니다.");
    }
}