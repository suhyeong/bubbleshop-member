package com.bubbleshop.member.domain.service;

import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.model.view.ProductView;
import com.bubbleshop.member.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetReviewProductInfoService {
    private final ProductRepository productRepository;

    public List<Review> getProductDetailInfoByReview(List<Review> reviews) {
        // 1. 리뷰의 상품 정보 조회
        Set<String> productIds = reviews.stream().map(Review::getProductCode).collect(Collectors.toSet());
        Map<String, ProductView> productInfo = productRepository.getProductList(productIds);
        // 2. 상품 정보를 리뷰 Aggregate 에 세팅
        reviews.forEach(review -> review.applyProductNameInfo(productInfo.get(review.getProductCode())));
        return reviews;
    }
}
