package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.command.UpdateReviewCommand;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCommandService {
    private final ReviewRepository reviewRepository;

    public Review updateReviewDetail(UpdateReviewCommand command) {
        Review review = reviewRepository.findById(command.getReviewNo()).orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));
        review.updateReviewShowYn(command.isShowYn());
        return reviewRepository.save(review);
    }
}
