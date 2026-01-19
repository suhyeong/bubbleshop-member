package com.bubbleshop.member.infrastructure.feign;

import com.bubbleshop.config.properties.NaverConfig;
import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.constant.TokenType;
import com.bubbleshop.member.domain.view.RequestMemberInfoView;
import com.bubbleshop.member.domain.view.RequestTokenView;
import com.bubbleshop.member.infrastructure.constants.NaverResponseCode;
import com.bubbleshop.member.infrastructure.dto.GetNaverMemberProfileRspDTO;
import com.bubbleshop.member.infrastructure.dto.NaverRspDTO;
import com.bubbleshop.member.infrastructure.dto.RequestNaverTokenReqDTO;
import com.bubbleshop.member.infrastructure.dto.RequestNaverTokenRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverFeignService {
    private final NaverConfig naverConfig;
    private final NaverOAuthFeignClient naverOAuthFeignClient;
    private final NaverOpenApiFeignClient naverOpenApiFeignClient;

    /**
     * FeignClient 통신에는 성공하여 200 OK 이지만, 에러가 발생했을 경우 에러 코드를 찾아 리턴
     * @param responseDto 요청 DTO
     */
    private void checkResponseDTO(NaverRspDTO responseDto) {
        if (StringUtils.isNotBlank(responseDto.getError()) ||
                (StringUtils.isNotBlank(responseDto.getResultCode()) &&
                        !NaverResponseCode.SUCCESS.getCode().equals(responseDto.getResultCode()))
        ) {
            String errorCode = StringUtils.defaultIfBlank(responseDto.getError(), responseDto.getResultCode());
            throw new ApiException(NaverResponseCode.find(errorCode));
        }
    }

    /**
     * 접근 토큰 발급/갱신/삭제 요청
     * @param code 로그인 code
     * @param state 로그인 state
     */
    public RequestTokenView requestAccessToken(String code, String state, String accessToken, String refreshToken, TokenType grantType) {
        try {
            RequestNaverTokenReqDTO requestDto = RequestNaverTokenReqDTO.builder()
                    .grantType(grantType.getNaverCode())
                    .clientId(naverConfig.getClientId())
                    .clientSecret(naverConfig.getClientSecret())
                    .code(code)
                    .state(state)
                    .build();

            // 토큰 요청 타입이 갱신 혹은 삭제일 경우 필요한 요청값 재세팅
            if(TokenType.REFRESH.equals(grantType)) {
                requestDto = requestDto.toBuilder().refreshToken(refreshToken).build();
            } else if(TokenType.DELETE.equals(grantType)) {
                requestDto = requestDto.toBuilder().accessToken(accessToken).serviceProvider("NAVER").build();
            }

            RequestNaverTokenRspDTO responseDto = naverOAuthFeignClient.requestAccessToken(requestDto);

            this.checkResponseDTO(responseDto);

            return new RequestTokenView(responseDto.getAccessToken(), responseDto.getRefreshToken());
        } catch (Exception e) {
            if(e instanceof ApiException) throw e;
            throw new ApiException(ResponseCode.SERVER_ERROR);
        } finally {
            // TODO EventListener 를 통해 토큰 요청 히스토리 내역 쌓기
        }
    }

    /**
     * 회원 정보 요청
     * @param accessToken AccessToken
     * @return 회원 정보 View
     */
    public RequestMemberInfoView requestMemberProfile(String accessToken) {
        try {
            GetNaverMemberProfileRspDTO responseDto = naverOpenApiFeignClient.getMemberInfo(naverConfig.getClientId(),
                    naverConfig.getClientSecret(), String.format("Bearer %s", accessToken));

            this.checkResponseDTO(responseDto);

            GetNaverMemberProfileRspDTO.GetNaverMemberProfileResult result = responseDto.getResult();
            
            return RequestMemberInfoView.builder()
                    .id(result.getId())
                    .name(result.getName())
                    .birth(result.getBirthday().replace(StaticValues.DASH, StringUtils.EMPTY)) // MMdd
                    .phone(result.getMobile()) // TODO 휴대폰 정보 형식 확정 필요
                    .email(result.getEmail())
                    .build();
        } catch (Exception e) {
            if(e instanceof ApiException) throw e;
            throw new ApiException(ResponseCode.SERVER_ERROR);
        } finally {
            // TODO EventListener 를 통해 회원 프로필 조회 요청 히스토리 내역 쌓기
        }
    }
}
