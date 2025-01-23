package com.bubbleshop.member.application.internal.queryservice;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.command.GetReviewListCommand;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.model.view.ReviewListView;
import com.bubbleshop.member.domain.model.view.ReviewView;
import com.bubbleshop.member.domain.repository.ReviewRepository;
import com.bubbleshop.member.domain.service.GetReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {
    private final ReviewRepository reviewRepository;
    private final GetReviewService getReviewService;

    public ReviewListView getReviewList(GetReviewListCommand command) {
        long count = reviewRepository.countReviewListWithPagination(command);
        List<ReviewView> reviews = reviewRepository.findReviewListWithPagination(command);
        reviews = getReviewService.getProductDetailInfoByReview(reviews);
        return new ReviewListView(count, reviews);
    }

    public ReviewView getReviewDetail(String reviewNo) {
        Review review = reviewRepository.findById(reviewNo).orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));
        return getReviewService.getProductDetailInfoByReview(review);
    }
}
