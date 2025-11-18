package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateLoginStateRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1934090161457897114L;

    private String state;
}
