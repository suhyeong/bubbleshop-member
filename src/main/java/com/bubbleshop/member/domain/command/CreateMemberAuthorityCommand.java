package com.bubbleshop.member.domain.command;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMemberAuthorityCommand {
    private String requestId;
    private String code;
    private String state;
    private MemberProviderType providerType;
}
