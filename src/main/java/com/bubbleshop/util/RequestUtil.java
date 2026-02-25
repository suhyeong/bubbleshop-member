package com.bubbleshop.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getClientIP(HttpServletRequest request) {
        String[] headerNames = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };

        for (String headerName : headerNames) {
            String ip = request.getHeader(headerName);
            if (ip != null && !ip.isBlank() && !"unknown".equalsIgnoreCase(ip)) {
                // X-Forwarded-For는 콤마로 여러 IP가 올 수 있어서 첫번째가 실제 클라이언트 IP
                return ip.split(",")[0].trim();
            }
        }

        return request.getRemoteAddr();
    }
}
