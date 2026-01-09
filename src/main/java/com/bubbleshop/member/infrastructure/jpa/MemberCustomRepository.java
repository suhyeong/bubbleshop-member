package com.bubbleshop.member.infrastructure.jpa;

import com.bubbleshop.member.domain.command.GetMemberListCommand;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.model.view.MemberView;

import java.util.List;
import java.util.Optional;

public interface MemberCustomRepository {
    long countMemberListWithPagination(GetMemberListCommand command);
    List<MemberView> findMemberListWithPagination(GetMemberListCommand command);
    Optional<Member> findMemberJoinMemberSocialAccount(MemberProviderType providerType, String provideId, String email);
}
