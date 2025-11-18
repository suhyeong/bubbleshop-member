package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.config.jwt.TokenProvider;
import com.bubbleshop.config.jwt.TokenView;
import com.bubbleshop.config.redis.RedisTemplateProvider;
import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

import static com.bubbleshop.constants.StaticValues.RedisKey.*;
import static com.bubbleshop.constants.StaticValues.Token.GUEST_ID_PREFIX;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthCommandService {
    private final TokenProvider tokenProvider;
    private final RedisTemplateProvider redisTemplateProvider;

    @Value("${jwt.refresh-expiration-time}")
    private Long refreshTokenExpirationTime;

    private String getRefreshTokenRedisKey(String userId) {
        return String.format("%s%s%s", REFRESH_TOKEN_KEY, REDIS_KEY_DIVIDER, userId);
    }

    /**
     * 기본 (비회원) 토큰 생성
     *
     * 1. 비회원 토큰을 생성한다.
     * 2. 토큰 View 의 Refresh Token 을 레디스에 저장한다. (cache name : mbr, key: rtk:{id})
     * 3. 토큰 View 의 Access Token 을 리턴한다.
     * @return
     */
    public TokenView createGuestToken() {
        // create guest id
        String guestId = GUEST_ID_PREFIX + UUID.randomUUID().toString();
        // create guest token
        return this.createGuestToken(guestId);
    }

    private TokenView createGuestToken(String guestId) {
        // create guest token
        TokenView view = tokenProvider.createGuestToken(guestId);
        // save refresh token to redis
        String redisKey = this.getRefreshTokenRedisKey(guestId);
        redisTemplateProvider.setRedisValue(redisKey, view.getRefreshToken(), refreshTokenExpirationTime, TimeUnit.MILLISECONDS); // TODO
        return view;
    }

    public TokenView refreshAccessToken(String cookieRefreshToken, String memberId) {
        // 1. 레디스에서 memberId 로 Refresh Token 조회 & 유효성 체크
        String redisToken = (String) redisTemplateProvider.getRedisValue(this.getRefreshTokenRedisKey(memberId));
        if(Objects.isNull(redisToken) || !cookieRefreshToken.equals(redisToken) || !tokenProvider.validateToken(redisToken)) {
            throw new ApiException(ResponseCode.UNAUTHORIZED);
        }

        // 2. 회원 아이디가 G 로 시작할 경우 게스트, 아닐 경우 회원 로직 수행
        if(memberId.startsWith(GUEST_ID_PREFIX)) {
            return this.createGuestToken(memberId);
        } else {
            return null; // TODO
        }
    }


}
