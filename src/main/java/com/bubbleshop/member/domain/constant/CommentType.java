package com.bubbleshop.member.domain.constant;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum CommentType {
    REVIEW("R", "리뷰"),
    QNA("Q", "Q&A")
    ;

    private final String code;
    private final String desc;

    private static final ImmutableMap<String, CommentType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(CommentType::getCode, Function.identity())));

    CommentType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CommentType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_TYPE);

        return codes.get(value);
    }
}
