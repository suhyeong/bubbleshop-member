package com.bubbleshop.member.domain.model.view;

import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.model.entity.ReviewImage;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewView {
    private String reviewNo;
    private String memberId;
    private String memberName;
    private String productCode;
    private String productName;
    private int productScore;
    private String reviewContent;
    private LocalDateTime createdDate;
    private boolean isReviewShow;
    private boolean isPayedPoint;

    private List<ReviewImageView> images;
    private List<CommentView> comments;

    @QueryProjection
    public ReviewView(Review review, Member member) {
        this.reviewNo = review.getReviewNo();
        this.memberId = review.getMemberId();
        this.memberName = member.getName();
        this.productCode = review.getProductCode();
        this.productScore = review.getProductScore();
        this.reviewContent = review.getReviewContents();
        this.isReviewShow = review.isShow();
        this.isPayedPoint = review.isPayedPoint();
        this.createdDate = review.getCreatedDate();
    }

    public ReviewView(Review review, Member member, ProductView product, List<Comment> comments, String imageUrl) {
        this(review, member);
        this.applyProductNameInfo(product);
        this.images = new ArrayList<>();
        review.getImages().forEach(image -> this.images.add(new ReviewImageView(image, imageUrl)));
        this.comments = new ArrayList<>();
        comments.forEach(comment -> this.comments.add(new CommentView(comment)));
    }

    /**
     * 상품명 정보 세팅
     * @param productView
     */
    public void applyProductNameInfo(ProductView productView) {
        if(Objects.nonNull(productView)) {
            this.productName = productView.getProductName();
        }
    }
}
