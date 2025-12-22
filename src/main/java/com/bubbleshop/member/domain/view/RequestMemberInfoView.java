package com.bubbleshop.member.domain.view;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestMemberInfoView {
    private String id;
    private String name;
    private String birth;
    private String phone;
}
