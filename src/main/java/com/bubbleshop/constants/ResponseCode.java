package com.bubbleshop.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    SUCCESS("00", "00", "정상적으로 성공하였습니다.", HttpStatus.OK),
    INVALID_PARAMETER("00", "03", "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST),
    NON_EXIST_DATA("00", "04", "데이터가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_DATA("00", "05", "동일한 키의 데이터가 존재합니다.", HttpStatus.BAD_REQUEST),
    DB_ERROR("00", "98", "DB 접속시 에러가 발생하였습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVER_ERROR("00", "99", "에러가 발생하였습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_TYPE("01", "01", "유효하지 않은 타입입니다.", HttpStatus.BAD_REQUEST),

    MEMBER_NOT_EXIST("02", "01", "회원 정보가 존재하지 않습니다. 아이디를 확인해주세요.", HttpStatus.BAD_REQUEST),
    MEMBER_PASSWORD_ERROR("02", "02", "비밀번호가 일치하지 않습니다. 비밀번호를 확인해주세요.", HttpStatus.BAD_REQUEST),

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

}
