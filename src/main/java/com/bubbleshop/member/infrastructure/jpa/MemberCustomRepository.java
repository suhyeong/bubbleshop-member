package com.bubbleshop.member.infrastructure.jpa;

import com.bubbleshop.member.domain.command.GetMemberListCommand;
import com.bubbleshop.member.domain.model.view.MemberView;

import java.util.List;

public interface MemberCustomRepository {
    long countMemberListWithPagination(GetMemberListCommand command);
    List<MemberView> findMemberListWithPagination(GetMemberListCommand command);
}
