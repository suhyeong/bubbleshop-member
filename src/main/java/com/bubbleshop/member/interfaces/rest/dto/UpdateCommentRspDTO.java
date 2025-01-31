package com.bubbleshop.member.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@ToString
public class UpdateCommentRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6119470824024131410L;

    private String content;
    private String modifiedDate;
}
