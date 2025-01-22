package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.member.domain.model.entity.ReviewImage;
import com.bubbleshop.member.domain.model.entity.TimeEntity;
import com.bubbleshop.member.domain.model.view.ProductView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "member_id")
    private String memberId;

    @Description("상품 코드")
    @Column(name = "prd_code")
    private String productCode;

    @Transient
    private String productName; // 상품명

    @Description("상품 점수")
    @Column(name = "prd_score")
    private int productScore;

    @Description("리뷰 내용")
    @Column(name = "rvw_cont")
    private String reviewContents;

    @OneToMany(mappedBy = "review", targetEntity = ReviewImage.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"review"})
    private List<ReviewImage> images = new ArrayList<>();

    /**
     * 상품명 정보 세팅
     * @param productView
     */
    public void applyProductNameInfo(ProductView productView) {
        if(Objects.nonNull(productView)) {
            this.productName = productView.getProductName();
        }
    }
}
