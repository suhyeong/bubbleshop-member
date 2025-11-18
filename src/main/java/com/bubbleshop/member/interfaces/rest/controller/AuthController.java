package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.config.jwt.TokenProvider;
import com.bubbleshop.config.jwt.TokenView;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.member.application.internal.commandservice.AuthCommandService;
import com.bubbleshop.member.application.internal.commandservice.MemberCommandService;
import com.bubbleshop.member.domain.model.valueobjects.LoginAuthorizeInfo;
import com.bubbleshop.member.interfaces.rest.dto.CreateLoginAuthorizeRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.CreateLoginStateRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberRspDTO;
import com.bubbleshop.member.interfaces.transform.CreateLoginAuthorizeCommandDTOAssembler;
import com.bubbleshop.member.interfaces.transform.LoginMemberCommandDTOAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bubbleshop.constants.StaticHeaders.FRONTOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.constants.StaticValues.Token.*;
import static com.bubbleshop.member.interfaces.rest.controller.AuthUrl.AUTH;
import static com.bubbleshop.member.interfaces.rest.controller.AuthUrl.AUTH_DEFAULT_URL;
import static com.bubbleshop.member.interfaces.rest.controller.MemberUrl.*;

@Tag(name = "Auth API", description = "인증/인가 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AUTH_DEFAULT_URL, headers = FRONTOFFICE_CHANNEL_HEADER)
public class AuthController extends BaseController {
    private final AuthCommandService authCommandService;

    @Value("${jwt.expiration-time}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refresh-expiration-time}")
    private Long refreshTokenExpirationTime;

    @Operation(summary = "토큰 조회 API", description = "기본 토큰 정보를 조회 및 체크한다.")
    @GetMapping(value = AUTH)
    public ResponseEntity<Void> getAuthority() {

        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @Operation(summary = "토큰 생성 API", description = "기본 비회원 토큰을 생성한다.")
    @PostMapping(value = AUTH)
    public ResponseEntity<Void> createAuthority(HttpServletResponse response) {
        TokenView view = authCommandService.createGuestToken();
        setTokenToCookie(response, ACCESS_TOKEN, view.getAccessToken(), accessTokenExpirationTime);
        setTokenToCookie(response, REFRESH_TOKEN, view.getRefreshToken(), refreshTokenExpirationTime);
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @Operation(summary = "토큰 수정 (리프레시) API", description = "만료된 엑세스 토큰을 리프레시한다.")
    @PutMapping(value = AUTH)
    public ResponseEntity<Void> refreshAuthority(HttpServletRequest request, HttpServletResponse response) {
        String memberId = (String) request.getAttribute(MEMBER_ID_REQUEST);
        String refreshToken = (String) request.getAttribute(REFRESH_TOKEN);
        TokenView view = authCommandService.refreshAccessToken(refreshToken, memberId);
        setTokenToCookie(response, ACCESS_TOKEN, view.getAccessToken(), accessTokenExpirationTime);
        setTokenToCookie(response, REFRESH_TOKEN, view.getRefreshToken(), refreshTokenExpirationTime);
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }
}
