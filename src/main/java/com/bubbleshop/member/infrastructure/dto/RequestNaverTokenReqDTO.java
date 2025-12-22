package com.bubbleshop.member.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import feign.Param;
import feign.form.FormProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestNaverTokenReqDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2270624065375094793L;

    @JsonProperty("grant_type")
    private String grantType; //발급:'authorization_code' 갱신:'refresh_token' 삭제: 'delete'

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("code")
    private String code; // 발급때 필수

    @JsonProperty("state")
    private String state; // 발급때 필수

    @JsonProperty("refresh_token")
    private String refreshToken; // 갱신때 필수

    @JsonProperty("access_token")
    private String accessToken; // 삭제때 필수

    @JsonProperty("service_provider")
    private String serviceProvider; // 삭제때 필수, 'NAVER' 로 세팅
}
