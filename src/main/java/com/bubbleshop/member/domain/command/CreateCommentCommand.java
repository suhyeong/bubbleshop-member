package com.bubbleshop.member.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentCommand {
    private String content;
    private String commentType;
    private String targetNo;
    private String createdBy;
}
