package com.task.penta.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        AuthResponseUtil.authResultResponseBody(response, HttpServletResponse.SC_UNAUTHORIZED, "인증에 실패 했습니다. 로그인이 필요합니다.");
    }
}
