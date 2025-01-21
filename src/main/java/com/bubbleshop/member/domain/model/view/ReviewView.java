package com.bubbleshop.member.domain.model.view;

import com.bubbleshop.member.domain.model.aggregate.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewView {
    private String reviewNo;
    private String productCode;
    private String productName;
    private int productScore;
    private String reviewContent;
    private LocalDateTime createdDate;
    // TODO 리뷰 공개 여부, 포인트 지급 여부 정보 추가

    public ReviewView(Review review) {
        this.reviewNo = review.getReviewNo();
        this.productCode = review.getProductCode();
        this.productName = review.getProductName();
        this.productScore = review.getProductScore();
        this.reviewContent = review.getReviewContents();
        this.createdDate = review.getCreatedDate();
    }
}
