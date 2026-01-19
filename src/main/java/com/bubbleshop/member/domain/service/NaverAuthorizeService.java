package com.bubbleshop.member.domain.service;

import com.bubbleshop.config.properties.NaverConfig;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.constant.TokenType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.repository.MemberRepository;
import com.bubbleshop.member.domain.view.RequestMemberInfoView;
import com.bubbleshop.member.domain.view.RequestTokenView;
import com.bubbleshop.member.infrastructure.feign.NaverFeignService;
import com.bubbleshop.util.EncodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverAuthorizeService extends AuthorizeService {
    private final NaverConfig naverConfig;
    private final NaverFeignService naverFeignService;
    private final MemberRepository memberRepository;

    @Override
    public MemberProviderType getAuthorizeProviderType() {
        return MemberProviderType.NAVER;
    }

    @Override
    public String getAuthorizeUrl(String state) {
        String callbackUrl = EncodeUtil.encodeStringWithUTF8(naverConfig.getLoginCallbackUrl());
        return String.format(naverConfig.getAuthorizeUrl(), naverConfig.getClientId(), callbackUrl, state);
    }

    /**
     * 네이버 로그인(회원가입) 처리
     *
     * 1. 로그인 인증 요청으로 받은 code, state 값을 사용하여 접근 토큰 발급 호출
     * 2. 접근 토큰 발급으로 받은 AccessToken 으로 네이버 회원 프로필 조회 호출
     * 3. 프로필 조회시 리턴 받은 아이디 기준으로 회원 여부 체크
     * 4. 기존에 존재하지 않은 회원일 경우 새 회원 정보 생성 후 저장 Create Member Aggregate And Save
     * @param code 로그인 Code
     * @param state 로그인 state
     * @return 회원 Aggregate
     */
    @Override
    public Member createMemberAuthority(String code, String state) {
        // 1. AccessToken 발급 요청
        RequestTokenView token = naverFeignService.requestAccessToken(code, state, null, null, TokenType.AUTHORIZATION);
        // 2. 회원 정보 조회
        RequestMemberInfoView memberInfoView = naverFeignService.requestMemberProfile(token.getAccessToken());
        // 3. 회원 존재 여부 체크
        Optional<Member> member = memberRepository.findMemberJoinMemberSocialAccount(this.getAuthorizeProviderType(), memberInfoView.getId(), memberInfoView.getEmail());
        // 4. 회원 정보 존재할 경우 리턴, 없을 경우 새 회원 생성
        return member.orElseGet(() -> {
                Member newMember = memberRepository.save(new Member(memberInfoView, this.getAuthorizeProviderType()));
                newMember.setNewMember();
                return newMember;
        });
    }
}
