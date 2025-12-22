package com.bubbleshop.member.domain.constant;

import lombok.Getter;

@Getter
public enum TokenType {
    AUTHORIZATION("발급", "authorization_code"),
    REFRESH("갱신", "refresh_token"),
    DELETE("삭제", "delete"),

    ;
    private final String desc;
    private final String naverCode;

    TokenType(String desc, String naverCode) {
        this.desc = desc;
        this.naverCode = naverCode;
    }
}
