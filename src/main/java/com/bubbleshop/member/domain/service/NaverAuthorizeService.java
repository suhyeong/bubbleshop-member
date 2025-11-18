package com.bubbleshop.member.domain.service;

import com.bubbleshop.config.properties.NaverConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverAuthorizeService extends AuthorizeService {
    private final NaverConfig naverConfig;

    @Override
    public String getAuthorizeUrl(String state) {
        return String.format(naverConfig.getAuthorizeUrl(), naverConfig.getClientId(), naverConfig.getLoginCallbackUrl(), state);
    }
}
