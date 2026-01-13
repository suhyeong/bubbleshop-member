package com.bubbleshop.member.domain.view;

import com.bubbleshop.config.jwt.TokenView;
import lombok.Getter;

@Getter
public class CreateMemberAuthorityView extends TokenView {
    private boolean isNewMember;

    public CreateMemberAuthorityView(TokenView tokenView, boolean isNewMember) {
        super(tokenView.getAccessToken(), tokenView.getAccessTokenExpirationTime(), tokenView.getRefreshToken(), tokenView.getRefreshTokenExpirationTime());
        this.isNewMember = isNewMember;
    }
}
