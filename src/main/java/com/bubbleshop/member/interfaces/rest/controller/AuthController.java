package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.config.jwt.RequestId;
import com.bubbleshop.config.jwt.TokenView;
import com.bubbleshop.member.application.internal.commandservice.AuthCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bubbleshop.constants.StaticHeaders.FRONTOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.constants.StaticValues.Token.*;
import static com.bubbleshop.member.interfaces.rest.controller.AuthUrl.AUTH;
import static com.bubbleshop.member.interfaces.rest.controller.AuthUrl.AUTH_DEFAULT_URL;

@Tag(name = "Auth API", description = "인증/인가 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AUTH_DEFAULT_URL, headers = FRONTOFFICE_CHANNEL_HEADER)
public class AuthController extends BaseController {
    private final AuthCommandService authCommandService;

    @Operation(summary = "토큰 생성 API", description = "기본 비회원 토큰을 생성한다.")
    @PostMapping(value = AUTH)
    public ResponseEntity<Void> createAuthority(HttpServletResponse response) {
        TokenView view = authCommandService.createGuestToken();
        setTokenToCookie(response, ACCESS_TOKEN, view.getAccessToken(), view.getAccessTokenExpirationTime());
        setTokenToCookie(response, REFRESH_TOKEN, view.getRefreshToken(), view.getRefreshTokenExpirationTime());
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @Operation(summary = "토큰 수정 (리프레시) API", description = "만료된 엑세스 토큰을 리프레시한다.")
    @PutMapping(value = AUTH)
    public ResponseEntity<Void> refreshAuthority(@RequestId String requestId, HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = (String) getRequestAttributeValue(request, REFRESH_TOKEN);
        TokenView view = authCommandService.refreshAccessToken(refreshToken, requestId);
        setTokenToCookie(response, ACCESS_TOKEN, view.getAccessToken(), view.getAccessTokenExpirationTime());
        setTokenToCookie(response, REFRESH_TOKEN, view.getRefreshToken(), view.getRefreshTokenExpirationTime());
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }
}
