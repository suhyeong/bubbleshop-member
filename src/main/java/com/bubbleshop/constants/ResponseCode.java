package com.bubbleshop.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    SUCCESS("00", "00", "정상적으로 성공하였습니다.", HttpStatus.OK),
    UNAUTHORIZED("00","01", "유효하지 않은 접근입니다.", HttpStatus.UNAUTHORIZED),
    SERVICE_UNAVAILABLE("00","02", "서비스를 이용할 수 없습니다.\n해당 오류가 반복될 경우 문의를 남겨주세요.", HttpStatus.SERVICE_UNAVAILABLE),
    INVALID_PARAMETER("00", "03", "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST),
    NON_EXIST_DATA("00", "04", "데이터가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_DATA("00", "05", "동일한 키의 데이터가 존재합니다.", HttpStatus.BAD_REQUEST),
    REQUEST_TIMEOUT("00", "97", "요청 유효시간이 초과되었습니다. 다시 시도해주세요.", HttpStatus.REQUEST_TIMEOUT),
    DB_ERROR("00", "98", "DB 접속시 에러가 발생하였습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVER_ERROR("00", "99", "에러가 발생하였습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_TYPE("01", "01", "유효하지 않은 타입입니다.", HttpStatus.BAD_REQUEST),

    SNS_MEMBER_NOT_EXIST("02", "01", "시도한 플랫폼에 계정이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),

    S3_PUT_DATA_ERROR("05", "01", "파일 업로드시 에러가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_COPY_DATA_ERROR("05","02", "파일 복제시 에러가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String cate;
    private final String code;
    private final String message;
    private final HttpStatus status;

    ResponseCode(String cate, String code, String message, HttpStatus status) {
        this.cate = cate;
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getResponseCode() {
        return this.cate + this.code;
    }

    public static String getReplaceMessage(ResponseCode responseCode, String... objects) {
        int index = 0;
        String message = responseCode.getMessage();
        for(String object : objects) {
            message = message.replace("{" + index + "}", object);
        }
        return message;
    }

}
