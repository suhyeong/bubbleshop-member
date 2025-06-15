package com.bubbleshop.member.domain.model.valueobject;

import com.bubbleshop.member.domain.model.converter.YOrNToBooleanConverter;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class MemberEmailInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -1747188241110312153L;

    @Description("이메일")
    @Column(name = "email", unique = true)
    private String email;

    @Description("이메일 수신 동의 여부")
    @Column(name = "email_recv_agree_yn")
    @Convert(converter = YOrNToBooleanConverter.class)
    private boolean isEmailReceiveAgree;
}
