package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateCommentContentReqDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6119470824024131410L;

    private String content;
}
