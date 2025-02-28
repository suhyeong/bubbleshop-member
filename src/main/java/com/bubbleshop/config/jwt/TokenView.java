package com.bubbleshop.config.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenView {
    private String accessToken;
    private String refreshToken;
}
