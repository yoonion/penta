package com.task.penta.config;

import com.task.penta.config.exception.CustomAccessDeniedHandler;
import com.task.penta.config.exception.CustomAuthenticationEntryPoint;
import com.task.penta.entity.user.SystemUserRoleEnum;
import com.task.penta.jwt.JwtAuthenticationFilter;
import com.task.penta.jwt.JwtAuthorizationFilter;
import com.task.penta.jwt.JwtUtil;
import com.task.penta.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 커스텀 인증 (Authentication) filter
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    // 커스텀 인가 (Authorization) filter
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    // HTTP METHOD 허용 filter
    @Bean
    public HttpMethodFilter httpMethodFilter() {
        return new HttpMethodFilter();
    }

    // 인증 (Authentication) 실패 핸들러
    @Bean
    public AuthenticationEntryPoint entryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    // 인가 (Authorization) 실패 핸들러
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    /**
     * Restful API security 설정 (/api/** 요청에만 적용됩니다.)
     */
    @Bean
    @Order(1)
    public SecurityFilterChain ApiSecurityFilterChain(HttpSecurity http) throws Exception {
        applyCommonSecurityConfig(http); // 공통 설정 적용

        // 인증 설정
        http.securityMatcher("/api/**"); // /api/** 요청에만 적용
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // 회원가입 POST 요청 API 접근 허용
                        .requestMatchers("/api/**").hasAuthority(SystemUserRoleEnum.ADMIN.getAuthority()) // admin 계정만 접근이 가능하다.
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // API 요청 예외 처리
        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .authenticationEntryPoint(entryPoint()) // 인증(Authentication) 예외처리 -> 401
                        .accessDeniedHandler(accessDeniedHandler()) // 인가(권한) 거부 핸들러 -> 403 (Forbidden)
        );

        return http.build();
    }

    /**
     * Web security 설정 (api 를 제외한 모든 웹 요청 설정입니다.)
     */
    @Bean
    @Order(2)
    public SecurityFilterChain WebSecurityFilterChain(HttpSecurity http) throws Exception {
        applyCommonSecurityConfig(http); // 공통 설정 적용

        // 인증 설정
        http.securityMatcher("/**"); // api 권한은 ApiSecurityConfig 에서 확인. 나머지 모든 경로는 여기서 확인한다.
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/admin/**").hasAuthority(SystemUserRoleEnum.ADMIN.getAuthority()) // admin 권한 필요
                        .requestMatchers("/user/signup").permitAll() // 회원가입 페이지 접근 허용
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // 로그인 설정
        http.formLogin((formLogin) ->
                formLogin
                        // 커스텀 로그인 View 설정 (GET /user/login)
                        .loginPage("/user/login").permitAll()
        );

        // 접근 불가 페이지 설정 (forbidden)
        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .accessDeniedPage("/forbidden.html")
        );

        return http.build();
    }

    /**
     * Spring security 공통 설정
     */
    private void applyCommonSecurityConfig(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // security filter chain 순서 설정 - 구현한 인증 및 인가 filter 순서를 설정
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(httpMethodFilter(), JwtAuthorizationFilter.class); // http method 제한

    }

}
