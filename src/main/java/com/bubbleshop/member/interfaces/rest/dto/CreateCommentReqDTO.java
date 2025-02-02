package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateCommentReqDTO {
    private String content;
    private String commentType;
    private String targetNo;
}
