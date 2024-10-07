package com.task.penta.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthResponseUtil {

    public static void authResultResponseBody(HttpServletResponse response, int httpServletResponseStatusCode, String resultMessage) throws IOException {
        response.setStatus(httpServletResponseStatusCode);
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(new AuthResultResponseDto(httpServletResponseStatusCode, resultMessage));
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse);
        writer.flush();
        writer.close();
    }
}
