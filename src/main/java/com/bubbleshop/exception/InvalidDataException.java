package com.bubbleshop.exception;

import com.bubbleshop.constants.ResponseCode;

public class InvalidDataException extends ApiException {
    public InvalidDataException(ResponseCode responseCode) {
        super(responseCode);
    }
}
