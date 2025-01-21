package com.bubbleshop.member.infrastructure.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetProductDetailRspDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7148602891603914637L;

    private String productName;
}
