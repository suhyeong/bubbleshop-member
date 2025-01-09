package com.bubbleshop.constants;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ValidationExceptionType {
    BLANK("blank"),

    ;

    private final String type;

    ValidationExceptionType(String type) {
        this.type = type;
    }

    private static final ImmutableMap<String, ValidationExceptionType> types = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(ValidationExceptionType::getType, Function.identity())));
}
