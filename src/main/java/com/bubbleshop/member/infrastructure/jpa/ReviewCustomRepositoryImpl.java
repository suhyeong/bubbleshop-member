package com.bubbleshop.member.infrastructure.jpa;

import com.bubbleshop.member.domain.command.GetReviewListCommand;
import com.bubbleshop.member.domain.model.view.ReviewView;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

import static com.bubbleshop.member.domain.model.aggregate.QMember.member;
import static com.bubbleshop.member.domain.model.aggregate.QReview.review;


public class ReviewCustomRepositoryImpl extends QuerydslRepositorySupport implements ReviewCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ReviewCustomRepositoryImpl.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public long countReviewListWithPagination(GetReviewListCommand command) {
        return jpaQueryFactory
                .select(review.reviewNo)
                .from(review)
                .where(this.conditionReviewList(command))
                .fetch().size();
    }

    @Override
    public List<ReviewView> findReviewListWithPagination(GetReviewListCommand command) {
        return jpaQueryFactory
                .select(Projections.constructor(ReviewView.class, review, member))
                .from(review, member)
                .where(this.conditionReviewList(command).and(member.id.eq(review.memberId)))
                .limit(command.getPageable().getPageSize())
                .offset(command.getPageable().getOffset())
                .fetch();
    }

    private BooleanBuilder conditionReviewList(GetReviewListCommand command) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(command.getMemberId())) {
            booleanBuilder.and(review.memberId.eq(command.getMemberId()));
        }

        if(StringUtils.isNotBlank(command.getProductCode())) {
            booleanBuilder.and(review.productCode.eq(command.getProductCode()));
        }

        if(Objects.nonNull(command.getCreatedStartDate()) && Objects.nonNull(command.getCreatedEndDate())) {
            booleanBuilder.and(review.createdDate.between(command.getCreatedStartDate(), command.getCreatedEndDate()));
        }

        return booleanBuilder;
    }
}
