package com.bubbleshop.member.domain.constant;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum MemberAccessActionType {
    JOIN("J"),
    LOGIN("L"),

    ;
    private final String code;

    private static final ImmutableMap<String, MemberAccessActionType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(MemberAccessActionType::getCode, Function.identity())));

    MemberAccessActionType(String code) {
        this.code = code;
    }

    public static MemberAccessActionType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_TYPE);

        return codes.get(value);
    }

}
