package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.member.domain.model.entity.TimeEntity;
import com.bubbleshop.member.domain.model.valueobject.MemberEmailInfo;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "member_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Member extends TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 7976024044942500205L;

    @Id
    @Description("회원 아이디")
    @Column(name = "member_id")
    private String id;

    @Description("비밀번호")
    @Column(name = "passwd") // todo 암호화
    private String password;

    @Description("회원명")
    @Column(name = "member_name")
    private String name;

    @Description("회원 닉네임")
    @Column(name = "member_nickname")
    private String nickname;

    @Description("전화번호")
    @Column(name = "phone_num") // todo 암호화
    private String phoneNum;

    @Description("가입 일시")
    @Column(name = "join_dt", nullable = false)
    private LocalDateTime joinDate;

    @Description("탈퇴 일시")
    @Column(name = "withdrawal_dt")
    private LocalDateTime withdrawalDate;

    @Description("생년월일")
    @Column(name = "birth_dt")
    private LocalDateTime birthDate;

    @Description("회원 이메일 정보")
    @Embedded
    private MemberEmailInfo memberEmailInfo;

    @Description("포인트")
    @Column(name = "point")
    private int point;

    @Transient
    private LocalDateTime leftDateToDiscardMemberInfo; // 탈퇴한 회원일 경우 정보 삭제까지 남은 일자

    /**
     * 탈퇴 회원일 경우 정보 삭제까지 남은 기간 계산
     */
    public void calcLeftDateToDiscardMemberInfo() {
        if(Objects.nonNull(this.withdrawalDate)) {
            // todo
        }
    }
}
