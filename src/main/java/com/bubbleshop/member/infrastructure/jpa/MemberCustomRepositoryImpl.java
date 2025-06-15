package com.bubbleshop.member.infrastructure.jpa;

import com.bubbleshop.member.domain.command.GetMemberListCommand;
import com.bubbleshop.member.domain.model.view.MemberView;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

import static com.bubbleshop.member.domain.model.aggregate.QMember.member;

public class MemberCustomRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MemberCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(MemberCustomRepositoryImpl.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public long countMemberListWithPagination(GetMemberListCommand command) {
        return jpaQueryFactory
                .select(member.id)
                .from(member)
                .where(this.conditionMemberList(command))
                .fetch().size();
    }

    @Override
    public List<MemberView> findMemberListWithPagination(GetMemberListCommand command) {
        return jpaQueryFactory
                .select(Projections.constructor(MemberView.class, member))
                .from(member)
                .where(this.conditionMemberList(command))
                .limit(command.getPageable().getPageSize())
                .offset(command.getPageable().getOffset())
                .fetch();
    }

    private BooleanBuilder conditionMemberList(GetMemberListCommand command) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(command.getMemberId())) {
            booleanBuilder.and(member.id.eq(command.getMemberId()));
        }

        if(StringUtils.isNotBlank(command.getMemberNickname())) {
            if(command.isNameContains())
                booleanBuilder.and(member.nickname.contains(command.getMemberNickname()));
            else
                booleanBuilder.and(member.nickname.eq(command.getMemberNickname()));
        }

        if(Objects.nonNull(command.getJoinStartDate()) && Objects.nonNull(command.getJoinEndDate())) {
            booleanBuilder.and(member.joinDate.between(command.getJoinStartDate(), command.getJoinEndDate()));
        }

        return booleanBuilder;
    }
}
