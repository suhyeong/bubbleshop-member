package com.bubbleshop.member.interfaces.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
    private Boolean isReviewShow;
    private Boolean isPayedPoint;
}
