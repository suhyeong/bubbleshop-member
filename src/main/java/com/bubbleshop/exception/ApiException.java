package com.bubbleshop.exception;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.member.infrastructure.constants.NaverResponseCode;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -811503761761409409L;

    private String resultCode;
    private String resultMessage;
    private int httpStatus;

    // 외부 호출 이후 에러 반환시 외부 호출 결과값을 세팅
    private String externalResultCode;
    private String externalResultMessage;

    public ApiException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.resultCode = responseCode.getResponseCode();

        this.resultMessage = responseCode.getMessage();
        this.httpStatus = responseCode.getStatus().value();
    }

    public ApiException(ResponseCode responseCode, String externalResultCode, String externalResultMessage) {
        super(responseCode.getMessage());
        this.resultCode = responseCode.getResponseCode();
        this.resultMessage = responseCode.getMessage();
        this.httpStatus = responseCode.getStatus().value();

        this.externalResultCode = externalResultCode;
        this.externalResultMessage = externalResultMessage;
    }

    public ApiException(ResponseCode responseCode, String message) {
        super(message);
        this.resultCode = responseCode.getResponseCode();
        this.resultMessage = message;
        this.httpStatus = responseCode.getStatus().value();
    }

    public ApiException(String resultCode, String resultMessage, int httpStatus) {
        super(resultMessage);
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.httpStatus = httpStatus;
    }

}
