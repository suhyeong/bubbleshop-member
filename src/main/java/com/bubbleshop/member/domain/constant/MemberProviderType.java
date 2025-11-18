package com.bubbleshop.member.domain.constant;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum MemberProviderType {
    NAVER("N", "naver"),
    KAKAO("K", "kakao")

    ;
    private final String code;
    private final String path;

    private static final ImmutableMap<String, MemberProviderType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(MemberProviderType::getCode, Function.identity())));

    private static final ImmutableMap<String, MemberProviderType> paths = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(MemberProviderType::getPath, Function.identity())));

    MemberProviderType(String code, String path) {
        this.code = code;
        this.path = path;
    }

    public static MemberProviderType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_TYPE);

        return codes.get(value);
    }

    public static MemberProviderType findPath(String value) {
        if(!paths.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_TYPE);

        return paths.get(value);
    }
}
