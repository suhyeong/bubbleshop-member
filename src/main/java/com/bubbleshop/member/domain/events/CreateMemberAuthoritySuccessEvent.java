package com.bubbleshop.member.domain.events;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import lombok.Getter;

@Getter
public class CreateMemberAuthoritySuccessEvent {
    private final String memberId;
    private final boolean isNewMember;
    private final MemberProviderType providerType; //로그인 SNS 타입
    private final String accessIP; //접근 IP

    public CreateMemberAuthoritySuccessEvent(Member member, MemberProviderType providerType, String accessIP) {
        this.memberId = member.getId();
        this.isNewMember = member.isNewMember();
        this.providerType = providerType;
        this.accessIP = accessIP;
    }
}
