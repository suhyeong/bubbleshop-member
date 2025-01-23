package com.bubbleshop.member.domain.model.view;

import com.bubbleshop.member.domain.model.entity.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewImageView {
    private int sequence;
    private String path;
    private String fullPath;

    public ReviewImageView(ReviewImage reviewImage, String imageUrl) {
        this.sequence = reviewImage.getReviewImageId().getReviewImageSeq();
        this.path = reviewImage.getReviewImagePath();
        this.fullPath = imageUrl.concat("/").concat(reviewImage.getReviewImageId().getReviewNo()).concat("/").concat(this.path);
    }
}
