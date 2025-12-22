package com.bubbleshop.member.domain.service;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizeServiceStrategy {
    private final NaverAuthorizeService naverAuthorizeService;
    private final KakaoAuthorizeService kakaoAuthorizeService;

    public AuthorizeService getAuthorizeService(MemberProviderType providerType) {
        if (providerType.equals(MemberProviderType.NAVER)) {
            return naverAuthorizeService;
        } else if (providerType.equals(MemberProviderType.KAKAO)) {
            return kakaoAuthorizeService;
        } else return null;
    }
}
