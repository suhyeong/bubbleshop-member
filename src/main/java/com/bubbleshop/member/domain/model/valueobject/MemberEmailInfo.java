package com.bubbleshop.member.domain.model.valueobject;

import com.bubbleshop.member.domain.model.converter.YOrNToBooleanConverter;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class MemberEmailInfo implements Serializable {
    @Description("이메일")
    @Column(name = "email")
    private String email;

    @Description("이메일 수신 동의 여부")
    @Column(name = "email_recv_agree_yn")
    @Convert(converter = YOrNToBooleanConverter.class)
    private boolean isEmailReceiveAgree;
}
