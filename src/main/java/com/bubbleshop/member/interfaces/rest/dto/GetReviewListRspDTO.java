package com.bubbleshop.member.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@ToString
public class GetReviewListRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4424374242293406919L;

    private long count;
    private List<GetReviewRspDTO> reviewList;
}
