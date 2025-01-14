package com.bubbleshop.member.application.internal.queryservice;

import com.bubbleshop.member.domain.command.GetMemberListCommand;
import com.bubbleshop.member.domain.model.view.MemberListView;
import com.bubbleshop.member.domain.model.view.MemberView;
import com.bubbleshop.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public MemberListView getMemberList(GetMemberListCommand command) {
        long count = memberRepository.countMemberListWithPagination(command);
        List<MemberView> memberList = memberRepository.findMemberListWithPagination(command);
        return new MemberListView(count, memberList);
    }
}
