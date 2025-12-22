package com.bubbleshop.member.domain.service;

import com.bubbleshop.member.domain.constant.MemberAccessActionType;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public abstract class AuthorizeService {
    public String createState(MemberAccessActionType accessActionType) {
        SecureRandom random = new SecureRandom();
        String state = accessActionType.getCode().concat(new BigInteger(130, random).toString(31));
        return URLEncoder.encode(state, StandardCharsets.UTF_8);
    }

    public abstract MemberProviderType getAuthorizeProviderType();

    public abstract String getAuthorizeUrl(String state);

    public abstract Member joinMember(String code, String state);

    public abstract Member loginMember(String code, String state);
}
