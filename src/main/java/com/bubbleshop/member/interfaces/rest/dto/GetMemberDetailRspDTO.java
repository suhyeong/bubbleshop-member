package com.bubbleshop.member.interfaces.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class GetMemberDetailRspDTO extends GetMemberRspDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5147760606941358298L;

    private String birthDate;
    private String leftDateToDiscardMemberInfo; // 탈퇴한 회원일 경우 정보 삭제까지 남은 일자
    private int point;
    private String email;
    private Boolean isEmailReceiveAgree;
}
