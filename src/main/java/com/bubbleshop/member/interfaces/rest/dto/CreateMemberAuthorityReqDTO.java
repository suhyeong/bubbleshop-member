package com.bubbleshop.member.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateMemberAuthorityReqDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5549674672126310976L;
    @NotBlank
    private String state;
    @NotBlank
    private String code;
    @NotBlank
    private String provider;
}
