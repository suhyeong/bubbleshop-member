package com.bubbleshop.member.domain.model.entity;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.view.RequestMemberInfoView;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.io.Serial;

@Entity
@Table(name = "member_social_accnt_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class MemberSocialAccount extends TimeEntity {
    @Serial
    private static final long serialVersionUID = -3946309563362160090L;

    @EmbeddedId
    private MemberSocialAccountId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Member member;

    @Description("회원가입 제공 고유 아이디")
    @Column(name = "provider_id", nullable = false)
    private String providerId; // 회원가입시 가입 플랫폼에서 받은 해당 회원의 고유한 아이디

    @Description("회원가입 제공 이메일")
    @Column(name = "provider_email", nullable = false)
    private String providerEmail;

    public MemberSocialAccount(String id, RequestMemberInfoView memberInfoView, MemberProviderType providerType) {
        this.id = new MemberSocialAccountId(id, providerType);
        this.providerId = memberInfoView.getId();
        this.providerEmail = memberInfoView.getEmail();
    }

    public boolean isSameProviderType(MemberProviderType providerType) {
        return providerType.equals(this.id.getProviderType());
    }

    public String getProviderTypeName() {
        return this.id.getProviderType().getName();
    }
}
