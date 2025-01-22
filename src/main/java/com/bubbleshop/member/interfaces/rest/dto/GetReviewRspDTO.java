package com.bubbleshop.member.interfaces.rest.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetReviewRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6558142046357355960L;

    private String reviewNo;
    private String memberId;
    private String productCode;
    private String productName;
    private int productScore;
    private String reviewContent;
    private String createdDate;
    // TODO 리뷰 공개 여부, 포인트 지급 여부 정보 추가
}
