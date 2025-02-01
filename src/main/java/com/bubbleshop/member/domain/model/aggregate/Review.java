package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.member.domain.model.converter.YOrNToBooleanConverter;
import com.bubbleshop.member.domain.model.entity.ReviewImage;
import com.bubbleshop.member.domain.model.entity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Review extends TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6815492060945574091L;

    @Id
    @Description("리뷰 번호")
    @Column(name = "rvw_no")
    private String reviewNo;

    @Description("회원 아이디")
    @Column(name = "member_id", updatable = false)
    private String memberId;

    @Description("상품 코드")
    @Column(name = "prd_code", updatable = false)
    private String productCode;

    @Description("상품 점수")
    @Column(name = "prd_score")
    private int productScore;

    @Description("리뷰 내용")
    @Column(name = "rvw_cont")
    private String reviewContents;

    @Description("리뷰 공개 여부")
    @Column(name = "rvw_show_yn")
    @Convert(converter = YOrNToBooleanConverter.class)
    private boolean isShow;

    @Description("포인트 지급 여부")
    @Column(name = "point_pay_yn")
    @Convert(converter = YOrNToBooleanConverter.class)
    private boolean isPayedPoint;

    @OneToMany(mappedBy = "review", targetEntity = ReviewImage.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"review"})
    private List<ReviewImage> images = new ArrayList<>();

    public void updateReviewShowYn(boolean isShow) {
        this.isShow = isShow;
    }
}
