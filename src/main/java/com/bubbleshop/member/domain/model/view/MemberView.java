package com.bubbleshop.member.domain.model.view;

import com.bubbleshop.member.domain.model.aggregate.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberView {
    private String id;
    private String name;
    private String phoneNum;
    private LocalDateTime joinDate;
    private LocalDateTime withdrawalDate;

    @QueryProjection
    public MemberView(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.phoneNum = member.getPhoneNum();
        this.joinDate = member.getJoinDate();
        this.withdrawalDate = member.getWithdrawalDate();
    }
}
