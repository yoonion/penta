package com.task.penta.service;

import com.task.penta.dto.SystemUserCreateRequestDto;
import com.task.penta.dto.SystemUserCreateResponseDto;
import com.task.penta.entity.SystemUser;
import com.task.penta.repository.SystemUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SystemUserServiceTest {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("일반 회원 생성 테스트")
    void createNormalUserTest() {
        // Mock HttpServletRequest
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

        // 클라이언트 IP 주소를 반환하도록 설정 (ex: 192.168.0.1)
        when(mockRequest.getRemoteAddr()).thenReturn("192.168.0.1");

        // 일반 회원 생성
        SystemUserCreateRequestDto requestDto =
                new SystemUserCreateRequestDto(
                    "normalUser",
                        "1234",
                        "테스트일반회원"
                );

        SystemUserCreateResponseDto savedNormalUser = systemUserService.createUser(requestDto, mockRequest);

        // 검증
        assertThat(savedNormalUser.getUserId()).isEqualTo("normalUser");
        assertThat(savedNormalUser.getUserNm()).isEqualTo("테스트일반회원");
        assertThat(savedNormalUser.getUserAuth()).isEqualTo("SYSTEM_USER");
    }

    @Test
    @DisplayName("관리자 생성 테스트")
    void createAdminUserTest() {

        // admin 회원 생성
        SystemUser adminUser = new SystemUser(
                "admintest",
                passwordEncoder.encode("1234"),
                "관리자테스트유저",
                "SYSTEM_ADMIN"
        );

        SystemUser savedAdminUser = systemUserRepository.save(adminUser);

        // 검증
        assertThat(savedAdminUser.getUserId()).isEqualTo("admintest");
        assertThat(savedAdminUser.getUserNm()).isEqualTo("관리자테스트유저");
        assertThat(savedAdminUser.getUserAuth()).isEqualTo("SYSTEM_ADMIN");
    }
}