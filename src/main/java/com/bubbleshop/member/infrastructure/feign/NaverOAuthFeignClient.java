package com.bubbleshop.member.infrastructure.feign;

import com.bubbleshop.member.infrastructure.dto.RequestNaverTokenReqDTO;
import com.bubbleshop.member.infrastructure.dto.RequestNaverTokenRspDTO;
import com.bubbleshop.member.infrastructure.feign.config.NaverClientConfig;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import static com.bubbleshop.member.infrastructure.feign.NaverUrl.TOKEN;

@FeignClient(name = "naver-oauth", url = "${host.naver-oauth}", configuration = NaverClientConfig.class)
public interface NaverOAuthFeignClient {
    @GetMapping(value = TOKEN)
    RequestNaverTokenRspDTO requestAccessToken(@SpringQueryMap RequestNaverTokenReqDTO reqDTO);
}