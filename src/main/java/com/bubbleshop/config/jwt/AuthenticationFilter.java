package com.bubbleshop.config.jwt;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.util.EncodeUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public abstract class AuthenticationFilter extends OncePerRequestFilter {
    abstract protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException;
    abstract protected boolean shouldNotFilter(@NonNull HttpServletRequest request);

    public void setErrorResponse(@NonNull HttpServletResponse response, ResponseCode responseCode) {
        response.setStatus(responseCode.getStatus().value());
        response.setHeader(StaticValues.RESULT_CODE, responseCode.getCode());
        response.setHeader(StaticValues.RESULT_MESSAGE, EncodeUtil.encodeStringWithUTF8(responseCode.getMessage()));
    }
}
