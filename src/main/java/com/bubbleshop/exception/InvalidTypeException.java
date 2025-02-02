package com.bubbleshop.exception;

import com.bubbleshop.constants.ResponseCode;

import java.io.Serial;

public class InvalidTypeException extends ApiException {
    @Serial
    private static final long serialVersionUID = 1707636858535861949L;

    public InvalidTypeException(ResponseCode responseCode) {
        super(responseCode);
    }
}
