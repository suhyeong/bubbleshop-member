package com.bubbleshop.member.interfaces.rest.controller;

public class MemberUrl {
    public static final String MEMBER_DEFAULT_URL = "/member/v1";
    public static final String MEMBERS = "members";
    public static final String MEMBER = MEMBERS + "/{memberId}";

    public static final String AUTH_PAGE = "/auth/{provider}/page";
    public static final String AUTH_STATE = "/auth/state";
}
