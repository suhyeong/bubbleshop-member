package com.bubbleshop.member.domain.service;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.constant.CommentType;
import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.model.view.ProductView;
import com.bubbleshop.member.domain.model.view.ReviewView;
import com.bubbleshop.member.domain.repository.CommentRepository;
import com.bubbleshop.member.domain.repository.MemberRepository;
import com.bubbleshop.member.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetReviewService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;

    @Value("${image.url.review}")
    private String imageUrl;

    public List<ReviewView> getProductDetailInfoByReview(List<ReviewView> reviews) {
        // 1. 리뷰의 상품 정보 조회
        Set<String> productIds = reviews.stream().map(ReviewView::getProductCode).collect(Collectors.toSet());
        Map<String, ProductView> productInfo = productRepository.getProductList(productIds);
        // 2. 상품 정보를 리뷰 Aggregate 에 세팅
        reviews.forEach(review -> review.applyProductNameInfo(productInfo.get(review.getProductCode())));
        return reviews;
    }

    public ReviewView getProductDetailInfoByReview(Review review) {
        // 1. 회원 정보 조회 TODO 코드정리
        Member member = memberRepository.findById(review.getMemberId()).orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));
        // 2. 리뷰의 상품 정보 조회
        Map<String, ProductView> productInfo = productRepository.getProductList(List.of(review.getProductCode()));
        // 3. 리뷰의 댓글 리스트 조회
        List<Comment> comments = commentRepository.findCommentsByTargetNoAndCommentType(review.getReviewNo(), CommentType.REVIEW);
        return new ReviewView(review, productInfo.get(review.getProductCode()), comments, imageUrl);
    }
}
