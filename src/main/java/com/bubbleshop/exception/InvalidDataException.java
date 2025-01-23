package com.bubbleshop.exception;

import com.bubbleshop.constants.ResponseCode;

import java.io.Serial;

public class InvalidDataException extends ApiException {
    @Serial
    private static final long serialVersionUID = -8095528582551384055L;

    public InvalidDataException(ResponseCode responseCode) {
        super(responseCode);
    }
}
