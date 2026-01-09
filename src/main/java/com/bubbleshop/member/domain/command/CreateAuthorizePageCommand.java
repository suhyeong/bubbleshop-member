package com.bubbleshop.member.domain.command;

import com.bubbleshop.member.domain.constant.MemberAccessActionType;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAuthorizePageCommand {
    private String requestId;
    private MemberProviderType providerType;
}
