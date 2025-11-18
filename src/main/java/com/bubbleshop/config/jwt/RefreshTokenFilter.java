package com.bubbleshop.config.jwt;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Objects;

/**
 * Refresh Token Filter
 * Refresh Token 의 유효성을 체크한다.
 * 토큰 리프레시 API 만 Filter
 *
 */
@RequiredArgsConstructor
public class RefreshTokenFilter extends AuthenticationFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 1. Refresh Token 조회
        String cookieToken = tokenProvider.resolveToken(request, StaticValues.Token.REFRESH_TOKEN);

        // 2. Refresh Token 유효성 체크
        if(Objects.isNull(cookieToken) || !tokenProvider.validateToken(cookieToken)) {
            setErrorResponse(response, ResponseCode.UNAUTHORIZED);
            return;
        }

        // 3. Refresh Token 에서 유저 아이디 조회
        String memberId = tokenProvider.getUserId(cookieToken);

        // 4. Request Attribute 세팅
        request.setAttribute(StaticValues.Token.REFRESH_TOKEN, cookieToken);
        request.setAttribute(StaticValues.Token.MEMBER_ID_REQUEST, memberId);

        filterChain.doFilter(request, response);
    }

    /**
     * Filter 를 체크할 URL 설정
     * - 토큰 리프레시 API
     * @param request
     * @return
     */
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return !(request.getRequestURI().startsWith("/auth/v1/auth") && request.getMethod().equals(HttpMethod.PUT.name()));
    }
}
