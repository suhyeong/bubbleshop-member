package com.bubbleshop.member.domain.repository;

import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.infrastructure.jpa.ReviewCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String>, ReviewCustomRepository {
    List<Review> findReviewsByMemberId(String memberId);
}
