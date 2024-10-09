package com.task.penta.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.penta.config.AuthResponseUtil;
import com.task.penta.dto.request.LoginRequestDto;
import com.task.penta.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성 (JwtAuthenticationFilter)")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login"); // 지정하지 않을 시, '/login' 이 default 값
    }

    /**
     * 로그인 시도 -> 인증 객체 생성 메서드
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 로그인 성공 시 호출되는 메서드
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        String role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserAuth();

        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);

        AuthResponseUtil.authResultResponseBody(response, HttpServletResponse.SC_OK, "로그인 성공");
    }

    /**
     * 로그인 실패 시 호출되는 메서드
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        AuthResponseUtil.authResultResponseBody(response, HttpServletResponse.SC_UNAUTHORIZED, "로그인 실패. 아이디와 비밀번호를 확인해주세요.");
    }
}