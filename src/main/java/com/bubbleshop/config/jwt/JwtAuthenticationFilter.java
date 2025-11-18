package com.bubbleshop.config.jwt;

import com.bubbleshop.constants.StaticValues;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * JWT Authentication Filter
 * Cookie 에 저장되어 있는 AccessToken 을 조회하여 유효한지 체크하는 필터
 * 가장 첫번째로 수행
 *
 * OncePerRequestFilter : 한 번의 요청에 한번만 동작하도록 보장하는 필터
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends AuthenticationFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 1. Request Header 의 JWT 조회 (Cookie 의 AccessToken 조회)
        String token = tokenProvider.resolveToken(request, StaticValues.Token.ACCESS_TOKEN);

        // 2. JWT 유효성 체크
        if(!Objects.isNull(token) && tokenProvider.validateToken(token)) {
            // 토근이 유효할 경우 SecurityContext 저장
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Filter 를 무시할 URL 설정
     * - 비회원 토큰 생성/리프레시 API
     * @param request
     * @return
     */
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/v1/auth");
    }
}
