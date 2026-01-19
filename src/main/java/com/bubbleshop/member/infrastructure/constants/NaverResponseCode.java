package com.bubbleshop.member.infrastructure.constants;

import com.bubbleshop.constants.ResponseCode;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum NaverResponseCode {
    SUCCESS(200, "00", "success", null),
    AUTHENTICATION_FAIL(401, "024", "Authentication failed / 인증에 실패했습니다.", ResponseCode.SERVICE_UNAVAILABLE),
    AUTHENTICATION_HEADER_ERROR(401, "028", "Authentication header not exists / OAuth 인증 헤더(authorization header)가 없습니다.", ResponseCode.SERVICE_UNAVAILABLE),
    FORBIDDEN(403, "403", "Forbidden / 호출 권한이 없습니다.", ResponseCode.SERVICE_UNAVAILABLE),
    NOT_FOUND(404, "404", "Not Found / 검색 결과가 없습니다.", ResponseCode.SNS_MEMBER_NOT_EXIST),
    SERVER_ERROR(500, "500", "Internal Server Error / 데이터베이스 오류입니다.", ResponseCode.SERVER_ERROR),

    INVALID_REQUEST(200, "invalid_request", "파라미터가 잘못되었거나 요청문이 잘못되었습니다.", ResponseCode.SERVICE_UNAVAILABLE),
    UNAUTHORIZED_CLIENT(200, "unauthorized_client", "인증받지 않은 인증 코드(authorization code)로 요청했습니다.", ResponseCode.SERVICE_UNAVAILABLE),
    UNSUPPORTED_RESPONSE(200, "unsupported_response_type", "정의되지 않은 반환 형식으로 요청했습니다.", ResponseCode.SERVICE_UNAVAILABLE),
    SERVER_ERROR_200(200, "server_error", "네이버 인증 서버의 오류로 요청을 처리하지 못했습니다.", ResponseCode.SERVER_ERROR),

    ;
    private final int status;
    private final String code;
    private final String message;
    private final ResponseCode responseCode;

    private static final ImmutableMap<String, NaverResponseCode> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(NaverResponseCode::getCode, Function.identity())));


    NaverResponseCode(int status, String code, String message, ResponseCode responseCode) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.responseCode = responseCode;
    }

    public static ResponseCode find(String code) {
        if(code.isBlank() || !codes.containsKey(code))
            return ResponseCode.SERVER_ERROR;

        NaverResponseCode responseCode = codes.get(code);
        return ObjectUtils.isEmpty(responseCode) ? ResponseCode.SERVER_ERROR : responseCode.getResponseCode();
    }


}
