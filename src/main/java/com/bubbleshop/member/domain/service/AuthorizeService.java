package com.bubbleshop.member.domain.service;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.util.EncodeUtil;

import java.math.BigInteger;
import java.security.SecureRandom;

public abstract class AuthorizeService {
    public String createState() {
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);
        return EncodeUtil.encodeStringWithUTF8(state);
    }

    public abstract MemberProviderType getAuthorizeProviderType();

    public abstract String getAuthorizeUrl(String state);

    public abstract Member createMemberAuthority(String code, String state);
}
