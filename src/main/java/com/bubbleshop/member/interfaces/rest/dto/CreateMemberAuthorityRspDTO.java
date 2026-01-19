package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateMemberAuthorityRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4951310827441913113L;
    private Boolean isNewMember;
}
