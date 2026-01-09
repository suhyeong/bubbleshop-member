package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.converter.MemberJoinPlatformTypeConverter;
import com.bubbleshop.member.domain.model.entity.MemberAuthority;
import com.bubbleshop.member.domain.model.entity.MemberSocialAccount;
import com.bubbleshop.member.domain.model.entity.TimeEntity;
import com.bubbleshop.member.domain.view.RequestMemberInfoView;
import com.bubbleshop.util.DateTimeUtils;
import jdk.jfr.Description;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.io.Serial;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.bubbleshop.util.DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS;

@Entity
@Table(name = "member_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@DynamicUpdate
public class Member extends TimeEntity {
    @Serial
    private static final long serialVersionUID = 7976024044942500205L;

    @Id
    @Description("회원 아이디")
    @Column(name = "member_id")
    private String id;

    @Description("회원명")
    @Column(name = "member_name") // todo 암호화
    private String name;

    // AES-256-GCM
    @Description("전화번호")
    @Column(name = "phone_num", unique = true) // todo 암호화
    private String phoneNum;

    // HMAC
    @Description("검색전용 해시 전화번호")
    @Column(name = "phone_num_hash", unique = true) // todo 암호화
    private String phoneNumHash;

    @Description("가입 일시")
    @Column(name = "join_dt", nullable = false)
    private LocalDateTime joinDate;

    @Description("탈퇴 일시")
    @Column(name = "withdrawal_dt")
    private LocalDateTime withdrawalDate;

    @Description("생년월일")
    @Column(name = "birth_dt")
    private String birthDate;

    // TODO Entity 로 빼기
    @Description("포인트")
    @Column(name = "point")
    private int point;

    @OneToMany(mappedBy = "member", targetEntity = MemberSocialAccount.class, cascade = CascadeType.ALL)
    private List<MemberSocialAccount> socialAccounts = new ArrayList<>(); //회원 연동 계정

    @Transient
    private LocalDateTime leftDateToDiscardMemberInfo; // 탈퇴한 회원일 경우 정보 삭제까지 남은 일자

    public Member(RequestMemberInfoView memberInfoView, MemberProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();
        String memberId = this.createMemberId(now);
        this.id = memberId;
        this.name = memberInfoView.getName();
        this.phoneNum = memberInfoView.getPhone(); // TODO
        this.phoneNumHash = memberInfoView.getPhone(); // TODO
        this.joinDate = now;
        this.birthDate = memberInfoView.getBirth(); // MMdd
        this.socialAccounts.add(new MemberSocialAccount(memberId, memberInfoView, providerType));
    }

    public String createMemberId(LocalDateTime now) {
        SecureRandom random = new SecureRandom();
        String randomStr = String.format("%03d", random.nextInt(1000));
        return StaticValues.Prefix.MEMBER_ID_PREFIX + DateTimeUtils.convertDateTimeToString(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS, now) + randomStr;
    }

    /**
     * 탈퇴 회원일 경우 정보 삭제까지 남은 기간 계산
     */
    public void calcLeftDateToDiscardMemberInfo() {
        if(Objects.nonNull(this.withdrawalDate)) {
            // todo
        }
    }

}
