package com.bubbleshop.member.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class GetReviewDetailRspDTO extends GetReviewRspDTO {
    @Serial
    private static final long serialVersionUID = -6636783837597964710L;

    private List<ReviewImageDTO> images;
    private List<ReviewCommentDTO> comments;

    @Getter
    @Builder
    @ToString
    public static class ReviewImageDTO implements Serializable {
        @Serial
        private static final long serialVersionUID = 105042243931233321L;

        private int sequence;
        private String path;
        private String fullPath;
    }

    @Getter
    @Builder
    @ToString
    public static class ReviewCommentDTO implements Serializable {

        @Serial
        private static final long serialVersionUID = 3788399214499034118L;

        private String commentNo;
        private String content;
        private String createdDate;
        private String createdBy;
    }
}
