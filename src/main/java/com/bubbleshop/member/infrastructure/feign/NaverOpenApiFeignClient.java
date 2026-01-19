package com.bubbleshop.member.infrastructure.feign;

import com.bubbleshop.member.infrastructure.dto.GetNaverMemberProfileRspDTO;
import com.bubbleshop.member.infrastructure.feign.config.NaverClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.bubbleshop.member.infrastructure.feign.NaverUrl.MEMBER_INFO;

@FeignClient(name = "naver-openapi", url = "${host.naver-openapi}", configuration = NaverClientConfig.class)
public interface NaverOpenApiFeignClient {
    @GetMapping(value = MEMBER_INFO)
    GetNaverMemberProfileRspDTO getMemberInfo(@RequestHeader(name = "X-Naver-Client-Id") String clientId,
                                              @RequestHeader(name = "X-Naver-Client-Secret") String clientSecret,
                                              @RequestHeader(name = "Authorization") String authorization);
}