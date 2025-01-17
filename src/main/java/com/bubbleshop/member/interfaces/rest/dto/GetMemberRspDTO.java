package com.bubbleshop.member.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@SuperBuilder
@ToString
public class GetMemberRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3549863338920331446L;

    private String id;
    private String name;
    private String nickname;
    private String phoneNum;
    private String joinDate;
    private String withdrawalDate;
}
