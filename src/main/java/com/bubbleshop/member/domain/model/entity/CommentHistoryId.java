package com.bubbleshop.member.domain.model.entity;

import jdk.jfr.Description;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class CommentHistoryId implements Serializable {
    @Serial
    private static final long serialVersionUID = -6036452083815583800L;

    @Description("코멘트 번호")
    @Column(name = "cmnt_no")
    private String commentNo;

    @Description("코멘트 이력 순번")
    @Column(name = "cmnt_hist_seq")
    private Integer sequence;
}
