package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BaseController {

    public HttpHeaders getSuccessHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(StaticValues.RESULT_CODE, ResponseCode.SUCCESS.getResponseCode());
        headers.add(StaticValues.RESULT_MESSAGE, URLEncoder.encode(ResponseCode.SUCCESS.getMessage(), StandardCharsets.UTF_8));
        return headers;
    }

    public HttpHeaders getErrorHeaders(List<ObjectError> bindingErrors) {
        HttpHeaders headers = new HttpHeaders();
        bindingErrors.forEach(error -> {
            FieldError fieldError = (FieldError) error;
            headers.add(StaticValues.RESULT_CODE, ResponseCode.NON_EXIST_DATA.getResponseCode());
            headers.add(StaticValues.RESULT_MESSAGE, fieldError.getDefaultMessage());
        });

        return headers;
    }
}
