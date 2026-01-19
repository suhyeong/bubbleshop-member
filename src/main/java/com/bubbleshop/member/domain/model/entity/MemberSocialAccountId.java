package com.bubbleshop.member.domain.model.entity;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.converter.MemberJoinPlatformTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jdk.jfr.Description;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class MemberSocialAccountId implements Serializable {
    @Serial
    private static final long serialVersionUID = -1607549876771479857L;

    @Column(name = "member_id")
    private String memberId;

    @Description("회원가입 제공 플랫폼 코드")
    @Column(name = "provider_code", nullable = false)
    @Convert(converter = MemberJoinPlatformTypeConverter.class)
    private MemberProviderType providerType;
}
