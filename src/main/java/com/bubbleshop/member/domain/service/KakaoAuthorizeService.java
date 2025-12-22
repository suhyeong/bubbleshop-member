package com.bubbleshop.member.domain.service;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoAuthorizeService extends AuthorizeService {
    @Override
    public MemberProviderType getAuthorizeProviderType() {
        return MemberProviderType.KAKAO;
    }

    @Override
    public String getAuthorizeUrl(String state) {
        return null; // TODO
    }

    @Override
    public Member joinMember(String code, String state) {
        return null; // TODO
    }

    @Override
    public Member loginMember(String code, String state) {
        return null; // TODO
    }
}
