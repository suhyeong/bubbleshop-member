package com.bubbleshop.member.domain.constant;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum HistoryType {
    CREATED("C", "생성"),
    UPDATED("U", "수정"),
    DELETED("D", "삭제"),
    ;

    private final String code;
    private final String desc;

    private static final ImmutableMap<String, HistoryType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(HistoryType::getCode, Function.identity())));

    HistoryType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static HistoryType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_TYPE);

        return codes.get(value);
    }
}
