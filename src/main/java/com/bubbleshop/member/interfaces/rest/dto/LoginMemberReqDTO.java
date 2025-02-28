package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginMemberReqDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4138986336400529257L;

    private String password;
}
