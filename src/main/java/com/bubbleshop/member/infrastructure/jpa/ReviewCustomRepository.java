package com.bubbleshop.member.infrastructure.jpa;

import com.bubbleshop.member.domain.command.GetReviewListCommand;
import com.bubbleshop.member.domain.model.view.ReviewView;

import java.util.List;

public interface ReviewCustomRepository {
    long countReviewListWithPagination(GetReviewListCommand command);
    List<ReviewView> findReviewListWithPagination(GetReviewListCommand command);
}
