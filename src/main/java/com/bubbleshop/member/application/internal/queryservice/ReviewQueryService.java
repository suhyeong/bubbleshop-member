package com.bubbleshop.member.application.internal.queryservice;

import com.bubbleshop.member.domain.command.GetReviewListCommand;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.model.view.ReviewListView;
import com.bubbleshop.member.domain.model.view.ReviewView;
import com.bubbleshop.member.domain.repository.ReviewRepository;
import com.bubbleshop.member.domain.service.GetReviewProductInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {
    private final ReviewRepository reviewRepository;
    private final GetReviewProductInfoService getReviewProductInfoService;

    public ReviewListView getReviewList(GetReviewListCommand command) {
        long count = reviewRepository.countReviewListWithPagination(command);
        List<Review> reviews = reviewRepository.findReviewListWithPagination(command);
        reviews = getReviewProductInfoService.getProductDetailInfoByReview(reviews);
        List<ReviewView> reviewViews = reviews.stream().map(ReviewView::new).collect(Collectors.toList());
        return new ReviewListView(count, reviewViews);
    }
}
