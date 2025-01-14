package com.bubbleshop.member.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class GetMemberListRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1295513271113369188L;

    private long count;
    private List<MemberRspDTO> memberList;

    @Getter
    @Builder
    public static class MemberRspDTO implements Serializable {
        @Serial
        private static final long serialVersionUID = 8896246062296306784L;

        private String id;
        private String name;
        private String nickname;
        private String phoneNum;
        private String joinDate;
        private String withdrawalDate;
    }
}
