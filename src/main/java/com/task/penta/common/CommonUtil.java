package com.task.penta.common;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

    // 클라이언트 IP 가져오기
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

//        if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
//            InetAddress address = InetAddress.getLocalHost();
//            ip = address.getHostName() + "/" + address.getHostAddress();
//        }

        return ip;
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
