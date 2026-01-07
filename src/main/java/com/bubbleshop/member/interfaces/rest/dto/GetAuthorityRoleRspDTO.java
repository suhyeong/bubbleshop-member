package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetAuthorityRoleRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2323890516354154839L;
    private Boolean isMember;
}
