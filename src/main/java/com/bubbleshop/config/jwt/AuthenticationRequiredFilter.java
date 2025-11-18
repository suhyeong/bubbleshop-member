package com.bubbleshop.config.jwt;

import com.bubbleshop.constants.ResponseCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * Authentication Required Filter
 * 인증 Authentication 체크
 * 가장 마지막에 수행하는 Filter (AccessToken, RefreshToken 모두 유효한 상태)
 *
 * OncePerRequestFilter : 한 번의 요청에 한번만 동작하도록 보장하는 필터
 */
@RequiredArgsConstructor
public class AuthenticationRequiredFilter extends AuthenticationFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 유효하지 않은 인증
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getAuthorities().isEmpty()) {
            setErrorResponse(response, ResponseCode.UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/auth/v1/auth");
    }
}
