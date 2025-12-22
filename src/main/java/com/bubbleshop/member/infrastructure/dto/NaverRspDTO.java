package com.bubbleshop.member.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NaverRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5689732301489610315L;

    @JsonProperty("error")
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("resultcode")
    private String resultCode;

    @JsonProperty("message")
    private String resultMessage;
}
