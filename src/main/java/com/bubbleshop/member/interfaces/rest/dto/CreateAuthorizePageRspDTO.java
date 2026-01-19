package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateAuthorizePageRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4348010873058359844L;
    private String url;
}
