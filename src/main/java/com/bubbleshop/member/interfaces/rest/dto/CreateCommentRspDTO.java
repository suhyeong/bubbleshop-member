package com.bubbleshop.member.interfaces.rest.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@SuperBuilder
@ToString
public class CreateCommentRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4117593154949095145L;

    private String commentNo;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private String createdBy;
}
