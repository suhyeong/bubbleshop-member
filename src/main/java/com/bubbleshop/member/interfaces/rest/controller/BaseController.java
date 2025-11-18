package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.constants.StaticValues.Token;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.bubbleshop.constants.StaticValues.Token.*;

public class BaseController {

    public HttpHeaders getSuccessHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(StaticValues.RESULT_CODE, ResponseCode.SUCCESS.getResponseCode());
        headers.add(StaticValues.RESULT_MESSAGE, URLEncoder.encode(ResponseCode.SUCCESS.getMessage(), StandardCharsets.UTF_8));
        return headers;
    }

    public HttpHeaders getErrorHeaders(List<ObjectError> bindingErrors) {
        HttpHeaders headers = new HttpHeaders();
        bindingErrors.forEach(error -> {
            FieldError fieldError = (FieldError) error;
            headers.add(StaticValues.RESULT_CODE, ResponseCode.NON_EXIST_DATA.getResponseCode());
            headers.add(StaticValues.RESULT_MESSAGE, fieldError.getDefaultMessage());
        });

        return headers;
    }

    /**
     * 쿠키에 요청 토큰 설정
     * @param response
     * @param name
     * @param token
     * @param maxAge
     */
    public void setTokenToCookie(HttpServletResponse response, String name, String token, Long maxAge) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);      // JavaScript 접근 불가
        cookie.setSecure(true);        // HTTPS에서만 전송
        cookie.setPath("/");           // 모든 경로에서 전송
        cookie.setMaxAge((int) (maxAge / TOKEN_SECONDS));
        cookie.setAttribute(SAME_SITE_ATTRIBUTE, "Strict"); //cookie.setSameSite("Strict"); CSRF 방어

        response.addCookie(cookie);
    }
}
