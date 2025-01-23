package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.model.view.CommentView;
import com.bubbleshop.member.domain.model.view.ReviewImageView;
import com.bubbleshop.member.domain.model.view.ReviewView;
import com.bubbleshop.member.interfaces.rest.dto.GetReviewDetailRspDTO;
import com.bubbleshop.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {DateTimeUtils.class})
public abstract class GetReviewDetailCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "createdDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, reviewView.getCreatedDate()) )"),
            @Mapping(target = "isReviewShow", source = "reviewShow"),
            @Mapping(target = "isPayedPoint", source = "payedPoint"),
            @Mapping(target = "images", qualifiedByName = "GetReviewDetailRspDTO.ReviewImageRspDTO"),
            @Mapping(target = "comments", qualifiedByName = "GetReviewDetailRspDTO.CommentRspDTO")
    })
    public abstract GetReviewDetailRspDTO toDTO(ReviewView reviewView);

    @Named("GetReviewDetailRspDTO.ReviewImageRspDTO")
    public abstract GetReviewDetailRspDTO.ReviewImageDTO toImageDTO(ReviewImageView imageView);

    @Named("GetReviewDetailRspDTO.CommentRspDTO")
    @Mapping(target = "createdDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, commentView.getCreatedDate()) )")
    public abstract GetReviewDetailRspDTO.ReviewCommentDTO toCommentDTO(CommentView commentView);
}
