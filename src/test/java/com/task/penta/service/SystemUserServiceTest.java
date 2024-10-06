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

    /**
     * 일반 회원 1, admin 회원 1 생성 테스트
     */
    @Test
    @DisplayName("일반 회원 및 관리자 회원 생성 테스트")
    void createUserTest() {
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
}