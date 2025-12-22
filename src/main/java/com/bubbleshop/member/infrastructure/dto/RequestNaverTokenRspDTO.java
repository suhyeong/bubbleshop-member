package com.bubbleshop.member.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class RequestNaverTokenRspDTO extends NaverRspDTO {
    @Serial
    private static final long serialVersionUID = 3352895160777246828L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken; // 발급 요청시에만 존재

    @JsonProperty("token_type")
    private String tokenType; //Bearer, MAC

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("result")
    private String result; // 삭제 요청시에만 존재
}
