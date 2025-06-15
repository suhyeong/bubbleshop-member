package com.bubbleshop.member.domain.model.entity;

import com.bubbleshop.member.domain.model.aggregate.Authority;
import com.bubbleshop.member.domain.model.aggregate.Member;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;

@Entity
@Table(name = "member_authority_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class MemberAuthority extends TimeEntity {
    @Serial
    private static final long serialVersionUID = 6000854571312489173L;

    @EmbeddedId
    private MemberAuthorityId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Member member;

    @OneToOne
    @JoinColumn(name = "authority_id", insertable = false, updatable = false)
    private Authority authority;
}
