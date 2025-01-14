package com.bubbleshop.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponseDto {
    private final String responseCode;
    private final String responseMessage;
}
