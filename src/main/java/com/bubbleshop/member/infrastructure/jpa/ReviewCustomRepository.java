package com.bubbleshop.member.infrastructure.jpa;

import com.bubbleshop.member.domain.command.GetReviewListCommand;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.model.view.ReviewView;

import java.util.List;

public interface ReviewCustomRepository {
    long countReviewListWithPagination(GetReviewListCommand command);
    List<Review> findReviewListWithPagination(GetReviewListCommand command);
}
