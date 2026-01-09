package com.bubbleshop.member.infrastructure.jpa;

import com.bubbleshop.member.domain.command.GetMemberListCommand;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.model.view.MemberView;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.netty.util.internal.ObjectUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.bubbleshop.member.domain.model.aggregate.QMember.member;
import static com.bubbleshop.member.domain.model.entity.QMemberSocialAccount.memberSocialAccount;

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

        if(Objects.nonNull(command.getJoinStartDate()) && Objects.nonNull(command.getJoinEndDate())) {
            booleanBuilder.and(member.joinDate.between(command.getJoinStartDate(), command.getJoinEndDate()));
        }

        return booleanBuilder;
    }

    /**
     * 회원 연동 소셜 정보로 회원 정보 조회
     * @param providerType 연동 소셜 타입
     * @param provideId 소셜 제공 ID
     * @param email 소셜 제공 이메일
     * @return Optional<Member>
     */
    public Optional<Member> findMemberJoinMemberSocialAccount(MemberProviderType providerType, String provideId, String email) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(memberSocialAccount.id.memberId.eq(member.id));

        if (ObjectUtils.isNotEmpty(provideId)) {
            booleanBuilder.and(memberSocialAccount.providerId.eq(provideId));
        }

        if (ObjectUtils.isNotEmpty(email)) {
            booleanBuilder.and(memberSocialAccount.providerEmail.eq(email));
        }

        if (ObjectUtils.isNotEmpty(providerType)) {
            booleanBuilder.and(memberSocialAccount.id.providerType.eq(providerType));
        }

        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(member)
                        .join(memberSocialAccount).on(booleanBuilder)
                        .fetchOne()
        );
    }
}
