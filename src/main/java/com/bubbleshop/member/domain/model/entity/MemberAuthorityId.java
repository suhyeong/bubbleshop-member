package com.bubbleshop.member.domain.model.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class MemberAuthorityId implements Serializable {
    @Serial
    private static final long serialVersionUID = 4351187511499267390L;

    @Column(name = "authority_id")
    private Long authorityId;

    @Column(name = "member_id")
    private String memberId;
}
