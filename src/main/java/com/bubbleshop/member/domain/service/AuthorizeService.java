package com.bubbleshop.member.domain.service;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public abstract class AuthorizeService {
    public String createState() {
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);
        return URLEncoder.encode(state, StandardCharsets.UTF_8);
    }

    public abstract MemberProviderType getAuthorizeProviderType();

    public abstract String getAuthorizeUrl(String state);

    public abstract Member createMemberAuthority(String code, String state);
}
