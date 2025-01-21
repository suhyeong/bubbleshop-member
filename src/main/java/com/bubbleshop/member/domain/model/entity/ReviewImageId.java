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
public class ReviewImageId implements Serializable {

    @Serial
    private static final long serialVersionUID = -7671898824296114670L;

    @Description("리뷰 번호")
    @Column(name = "rvw_no")
    private String reviewNo;

    @Description("리뷰 이미지 순번")
    @Column(name = "rvw_img_seq")
    private int reviewImageSeq;
}
