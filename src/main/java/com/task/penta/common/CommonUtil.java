package com.task.penta.common;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

    /**
     * 클라이언트 IP 가져오기
     * @param request HttpServletRequest 객체
     * @return 클라이언트 IP 주소
     */
    public static String getClientIp(HttpServletRequest request) {
        String clientIp = "";
        String[] headerKeys = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR",
                "X-Real-IP",
                "X-RealIP",
                "REMOTE_ADDR"
        };

        for (String headerKey : headerKeys) {
            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader(headerKey);
                // X-Forwarded-For 헤더는 여러 IP를 포함할 수 있으므로 첫 번째 IP를 사용
                if (clientIp != null && clientIp.contains(",")) {
                    clientIp = clientIp.split(",")[0].trim();
                }
            }
        }

        // 헤더에 IP가 없을 경우, request.getRemoteAddr() 사용
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    // 클라이언트 요청 url 가져오기
    public static String getRequestUrl(HttpServletRequest request) {
        // 전체 URL을 가져오기
        String requestUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString(); // 쿼리 문자열 가져오기
        if (queryString != null) {
            requestUrl += "?" + queryString; // 쿼리 문자열이 있을 경우 URL에 추가
        }

        return requestUrl;
    }
}
