package com.bubbleshop.member.domain.command;

import com.bubbleshop.member.domain.constant.MemberAccessActionType;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateLoginAuthorizeCommand {
    private MemberAccessActionType accessActionType;
    private MemberProviderType providerType;
}
