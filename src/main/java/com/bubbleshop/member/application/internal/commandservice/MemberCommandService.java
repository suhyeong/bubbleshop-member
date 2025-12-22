package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.config.jwt.TokenView;
import com.bubbleshop.config.redis.RedisTemplateProvider;
import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.exception.InvalidTypeException;
import com.bubbleshop.member.domain.command.CreateAuthorizePageCommand;
import com.bubbleshop.member.domain.command.CreateMemberAuthorityCommand;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.model.valueobjects.AuthorizePageInfo;
import com.bubbleshop.member.domain.service.AuthorizeService;
import com.bubbleshop.member.domain.service.AuthorizeServiceStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberCommandService {
    private final AuthorizeServiceStrategy authorizeServiceStrategy;
    private final RedisTemplateProvider redisTemplateProvider;
    private final AuthCommandService authCommandService;

    private AuthorizeService getAuthorizeServiceByProviderType(MemberProviderType providerType) {
        AuthorizeService authorizeService = authorizeServiceStrategy.getAuthorizeService(providerType);
        if (ObjectUtils.isEmpty(authorizeService)) {
            throw new ApiException(ResponseCode.SERVER_ERROR);
        }
        return authorizeService;
    }

    /**
     * 회원의 회원가입/로그인 진행
     * 1. 요청값의 state 값이 레디스에 존재하는지 체크
     * 2. 회원가입 요청/로그인 요청을 각 소셜 미디어에 따라 수행
     * @param command
     */
    public TokenView createMemberAuthority(CreateMemberAuthorityCommand command) {
        String key = String.format("%s%s%s", StaticValues.RedisKey.STATE_KEY, StaticValues.RedisKey.REDIS_KEY_DIVIDER, command.getRequestId());
        String redisState = redisTemplateProvider.getRedisValue(key, String.class);

        // state 레디스 값이 없거나 (유효기간 만료) 요청값과 레디스 값이 일치하지 않는 경우
        if(StringUtils.isBlank(redisState) || !StringUtils.equals(redisState, command.getState())) {
            throw new ApiException(ResponseCode.REQUEST_TIMEOUT);
        }

        AuthorizeService authorizeService = this.getAuthorizeServiceByProviderType(command.getProviderType());
        switch (command.getAccessActionType()) { // TODO 이벤트 핸들러 사용할건지
            case JOIN -> { return this.joinMember(command, authorizeService); }
            case LOGIN -> { return this.loginMember(command, authorizeService); }
            default -> throw new InvalidTypeException(ResponseCode.INVALID_TYPE);
        }
    }

    private TokenView joinMember(CreateMemberAuthorityCommand command, AuthorizeService authorizeService) {
        Member member = authorizeService.joinMember(command.getCode(), command.getState());
        return authCommandService.createMemberToken(member.getId());
    }

    private TokenView loginMember(CreateMemberAuthorityCommand command, AuthorizeService authorizeService) {
        Member member = authorizeService.loginMember(command.getCode(), command.getState());
        return authCommandService.createMemberToken(member.getId());
    }

    public AuthorizePageInfo createAuthorizePage(CreateAuthorizePageCommand command) {
        AuthorizeService authorizeService = this.getAuthorizeServiceByProviderType(command.getProviderType());

        String state = authorizeService.createState(command.getAccessActionType());
        String url = authorizeService.getAuthorizeUrl(state);

        // State 값 레디스에 저장 key = stt:{memberId}
        String key = String.format("%s%s%s", StaticValues.RedisKey.STATE_KEY, StaticValues.RedisKey.REDIS_KEY_DIVIDER, command.getRequestId());
        redisTemplateProvider.setRedisValue(key, state, 60000L, TimeUnit.MILLISECONDS);
        return new AuthorizePageInfo(url, state);
    }
}
