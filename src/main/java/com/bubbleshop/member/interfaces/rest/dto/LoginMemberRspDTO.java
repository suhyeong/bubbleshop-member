package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginMemberRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7295026743755137157L;
    private String accessToken;
}
