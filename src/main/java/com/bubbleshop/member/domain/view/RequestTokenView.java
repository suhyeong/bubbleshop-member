package com.bubbleshop.member.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTokenView {
    private String accessToken;
    private String refreshToken;
}
