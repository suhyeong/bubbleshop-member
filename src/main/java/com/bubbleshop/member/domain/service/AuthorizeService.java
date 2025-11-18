package com.bubbleshop.member.domain.service;

import java.math.BigInteger;
import java.security.SecureRandom;

public abstract class AuthorizeService {
    public String createState() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public abstract String getAuthorizeUrl(String state);
}
