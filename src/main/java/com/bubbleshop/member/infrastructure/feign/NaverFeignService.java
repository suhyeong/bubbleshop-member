package com.bubbleshop.member.infrastructure.feign;

import com.bubbleshop.config.properties.NaverConfig;
import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.constant.TokenType;
import com.bubbleshop.member.domain.events.CallExternalInterfaceEvent;
import com.bubbleshop.member.domain.view.RequestMemberInfoView;
import com.bubbleshop.member.domain.view.RequestTokenView;
import com.bubbleshop.member.infrastructure.constants.ExternalInterfaceType;
import com.bubbleshop.member.infrastructure.constants.NaverResponseCode;
import com.bubbleshop.member.infrastructure.dto.GetNaverMemberProfileRspDTO;
import com.bubbleshop.member.infrastructure.dto.NaverRspDTO;
import com.bubbleshop.member.infrastructure.dto.RequestNaverTokenReqDTO;
import com.bubbleshop.member.infrastructure.dto.RequestNaverTokenRspDTO;
import com.bubbleshop.util.FeignClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverFeignService {
    private final NaverConfig naverConfig;
    private final NaverOAuthFeignClient naverOAuthFeignClient;
    private final NaverOpenApiFeignClient naverOpenApiFeignClient;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${host.naver-oauth}")
    private String oauthHost;

    @Value("${host.naver-openapi}")
    private String openApiHost;

    /**
     * FeignClient 통신에는 성공하여 200 OK 이지만, 에러가 발생했을 경우 에러 코드를 찾아 리턴
     * @param responseDto 요청 DTO
     */
    private void checkResponseDTO(NaverRspDTO responseDto) {
        if (StringUtils.isNotBlank(responseDto.getError()) ||
                (StringUtils.isNotBlank(responseDto.getResultCode()) &&
                        !NaverResponseCode.SUCCESS.getCode().equals(responseDto.getResultCode()))
        ) {
            String errorCode = responseDto.getErrorCode();
            throw new ApiException(NaverResponseCode.find(errorCode), errorCode, responseDto.getErrorMessage());
        }
    }

    /**
     * 접근 토큰 발급/갱신/삭제 요청
     * @param code 로그인 code
     * @param state 로그인 state
     */
    public RequestTokenView requestAccessToken(String code, String state, String accessToken, String refreshToken, TokenType grantType) {
        CallExternalInterfaceEvent event = new CallExternalInterfaceEvent(ExternalInterfaceType.NAVER_REQUEST_ACCESS_TOKEN);

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

            // 이벤트 요청값 세팅
            event = FeignClientUtil.buildRequestEvent(event, oauthHost.concat(NaverUrl.TOKEN));

            RequestNaverTokenRspDTO responseDto = naverOAuthFeignClient.requestAccessToken(requestDto);

            this.checkResponseDTO(responseDto);

            // 이벤트 응답값 세팅
            // 해당 api 의 경우 네이버에서 연동 성공시에 code, message 값이 없으므로 성공시 공통 NaverResponseCode 로 대체
            event = FeignClientUtil.buildResponseEvent(event, NaverResponseCode.SUCCESS.getCode(), NaverResponseCode.SUCCESS.getMessage());
            return new RequestTokenView(responseDto.getAccessToken(), responseDto.getRefreshToken());
        } catch (Exception e) {
            if(e instanceof ApiException exception) {
                event = FeignClientUtil.buildResponseEvent(event, exception.getExternalResultCode(), exception.getExternalResultMessage());
                throw e;
            }
            throw new ApiException(ResponseCode.SERVER_ERROR);
        } finally {
            eventPublisher.publishEvent(event);
        }
    }

    /**
     * 회원 정보 요청
     * @param accessToken AccessToken
     * @return 회원 정보 View
     */
    public RequestMemberInfoView requestMemberProfile(String accessToken) {
        CallExternalInterfaceEvent event = new CallExternalInterfaceEvent(ExternalInterfaceType.NAVER_REQUEST_MEMBER_INFO);
        try {
            event = FeignClientUtil.buildRequestEvent(event, openApiHost.concat(NaverUrl.MEMBER_INFO));

            GetNaverMemberProfileRspDTO responseDto = naverOpenApiFeignClient.getMemberInfo(naverConfig.getClientId(),
                    naverConfig.getClientSecret(), String.format("Bearer %s", accessToken));

            this.checkResponseDTO(responseDto);

            event = FeignClientUtil.buildResponseEvent(event, responseDto.getResultCode(), responseDto.getResultMessage());

            GetNaverMemberProfileRspDTO.GetNaverMemberProfileResult result = responseDto.getResult();
            
            return RequestMemberInfoView.builder()
                    .id(result.getId())
                    .name(result.getName())
                    .birth(result.getBirthday().replace(StaticValues.DASH, StringUtils.EMPTY)) // MMdd
                    .phone(result.getMobile()) // TODO 휴대폰 정보 형식 확정 필요
                    .email(result.getEmail())
                    .build();
        } catch (Exception e) {
            if(e instanceof ApiException exception) {
                event = FeignClientUtil.buildResponseEvent(event, exception.getExternalResultCode(), exception.getExternalResultMessage());
                throw e;
            }
            throw new ApiException(ResponseCode.SERVER_ERROR);
        } finally {
            eventPublisher.publishEvent(event);
        }
    }
}
