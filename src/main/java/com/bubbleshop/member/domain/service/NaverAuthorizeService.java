package com.bubbleshop.member.domain.service;

import com.bubbleshop.config.properties.NaverConfig;
import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.constant.TokenType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.repository.MemberRepository;
import com.bubbleshop.member.domain.view.RequestMemberInfoView;
import com.bubbleshop.member.domain.view.RequestTokenView;
import com.bubbleshop.member.infrastructure.feign.NaverFeignService;
import com.bubbleshop.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.bubbleshop.util.DateTimeUtils.DATE_FORMAT_KOREA_YYYY_MM_DD;

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
        log.info("Naver Config :: {}", naverConfig);
        String callbackUrl = URLEncoder.encode(naverConfig.getLoginCallbackUrl(), StandardCharsets.UTF_8);
        return String.format(naverConfig.getAuthorizeUrl(), naverConfig.getClientId(), callbackUrl, state);
    }

    /**
     * 네이버 회원가입 처리
     *
     * 1. 로그인 인증 요청으로 받은 code, state 값을 사용하여 접근 토큰 발급 호출
     * 2. 접근 토큰 발급으로 받은 AccessToken 으로 네이버 회원 프로필 조회 호출
     * 3. 프로필 조회시 리턴 받은 아이디 기준으로 회원 여부 체크
     *  - 이미 회원가입 완료처리가 된 회원일 경우 에러 리턴
     * 4. 새 회원 정보 생성 후 저장 Create Member Aggregate And Save
     * @param code
     */
    @Override
    public Member joinMember(String code, String state) {
        // 1. AccessToken 발급 요청
        RequestTokenView token = naverFeignService.requestAccessToken(code, state, null, null, TokenType.AUTHORIZATION);
        // 2. 회원 정보 조회
        RequestMemberInfoView memberInfoView = naverFeignService.requestMemberProfile(token.getAccessToken());
        // 3. 회원 존재 여부 체크
        memberRepository.findByProviderId(memberInfoView.getId())
                .ifPresent((member) -> {
                    String joinDate = DateTimeUtils.convertDateTimeToString(DATE_FORMAT_KOREA_YYYY_MM_DD, member.getJoinDate());
                    throw new ApiException(ResponseCode.MEMBER_ALREADY_EXIST, ResponseCode.getReplaceMessage(ResponseCode.MEMBER_ALREADY_EXIST, joinDate));
                });
        // 4. 회원 어그리것 생성
        return memberRepository.save(new Member(memberInfoView, this.getAuthorizeProviderType()));
    }

    @Override
    public Member loginMember(String code, String state) {
        // 1. AccessToken 발급 요청
        RequestTokenView token = naverFeignService.requestAccessToken(code, state, null, null, TokenType.AUTHORIZATION);
        // 2. 회원 정보 조회
        RequestMemberInfoView memberInfoView = naverFeignService.requestMemberProfile(token.getAccessToken());
        // 3. 회원 존재 여부 체크
        return memberRepository.findByProviderId(memberInfoView.getId())
                .orElseThrow(() -> new ApiException(ResponseCode.MEMBER_NOT_EXIST));
    }
}
