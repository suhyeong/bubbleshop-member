package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.interfaces.rest.dto.UpdateCommentContentRspDTO;
import com.bubbleshop.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {DateTimeUtils.class})
public abstract class UpdateCommentContentDTOAssembler {

    @Mappings({
            @Mapping(target = "content", source = "comment.commentContent"),
            @Mapping(target = "modifiedDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, comment.getModifiedDate()) )"),
    })
    public abstract UpdateCommentContentRspDTO toDTO(Comment comment);
}
