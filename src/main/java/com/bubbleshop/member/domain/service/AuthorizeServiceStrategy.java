package com.bubbleshop.member.domain.service;

import com.bubbleshop.config.properties.NaverConfig;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizeServiceStrategy {
    private final NaverConfig naverConfig;

    public AuthorizeService getAuthorizeService(MemberProviderType providerType) {
        if (providerType.equals(MemberProviderType.NAVER)) {
            return new NaverAuthorizeService(naverConfig);
        } else if (providerType.equals(MemberProviderType.KAKAO)) {
            return new KakaoAuthorizeService();
        } else return null;
    }
}
