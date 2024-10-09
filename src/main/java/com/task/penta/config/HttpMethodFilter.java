package com.task.penta.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpMethodFilter extends OncePerRequestFilter {

    private static final List<String> ALLOWED_METHODS = Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String method = request.getMethod();
        if (!ALLOWED_METHODS.contains(method)) {
            AuthResponseUtil.authResultResponseBody(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "지원하지 않는 Http Method 입니다.");

            return;
        }

        filterChain.doFilter(request, response);
    }
}
