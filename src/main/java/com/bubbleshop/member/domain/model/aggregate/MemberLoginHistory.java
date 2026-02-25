package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.events.CreateMemberAuthoritySuccessEvent;
import com.bubbleshop.member.domain.model.converter.AccessIPConverter;
import com.bubbleshop.member.domain.model.converter.MemberJoinPlatformTypeConverter;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_login_history")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MemberLoginHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 2534873489776099632L;

    @Id
    @Description("회원 로그인 이력 순번")
    @Column(name = "login_hist_sn")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Description("회원 아이디")
    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Description("회원가입 제공 플랫폼 코드")
    @Column(name = "provider_code", nullable = false)
    @Convert(converter = MemberJoinPlatformTypeConverter.class)
    private MemberProviderType providerType;

    @Description("로그인 시도 IP")
    @Column(name = "login_ip", nullable = false)
    @Convert(converter = AccessIPConverter.class)
    private String ip;

    @Description("생성 일시")
    @Column(name = "crt_dt", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    public MemberLoginHistory(CreateMemberAuthoritySuccessEvent event) {
        this.memberId = event.getMemberId();
        this.providerType = event.getProviderType();
        this.ip = event.getAccessIP();
    }
}
