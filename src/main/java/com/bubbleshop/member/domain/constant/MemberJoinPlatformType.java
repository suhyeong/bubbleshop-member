package com.bubbleshop.member.domain.constant;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum MemberJoinPlatformType {
    NAVER("N"),
    KAKAO("K")

    ;
    private final String code;

    private static final ImmutableMap<String, MemberJoinPlatformType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(MemberJoinPlatformType::getCode, Function.identity())));

    MemberJoinPlatformType(String code) {
        this.code = code;
    }

    public static MemberJoinPlatformType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_TYPE);

        return codes.get(value);
    }
}
