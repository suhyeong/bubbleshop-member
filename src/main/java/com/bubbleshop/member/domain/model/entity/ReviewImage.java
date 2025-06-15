package com.bubbleshop.member.domain.model.entity;

import com.bubbleshop.member.domain.model.aggregate.Review;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "review_img_mng")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class ReviewImage extends TimeEntity {
    @Serial
    private static final long serialVersionUID = -3459536913566066567L;

    @EmbeddedId
    private ReviewImageId reviewImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rvw_no", insertable = false, updatable = false)
    @ToString.Exclude
    private Review review;

    @Description("리뷰 이미지 경로")
    @Column(name = "rvw_img_path")
    private String reviewImagePath;

}
