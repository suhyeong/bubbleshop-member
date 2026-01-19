package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.UpdateCommentCommand;
import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.interfaces.rest.dto.UpdateCommentReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.UpdateCommentRspDTO;
import com.bubbleshop.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {DateTimeUtil.class})
public abstract class UpdateCommentCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "content", source = "request.content")
    })
    public abstract UpdateCommentCommand toCommand(String commentNo, UpdateCommentReqDTO request);

    @Mappings({
            @Mapping(target = "content", source = "comment.commentContent"),
            @Mapping(target = "modifiedDate", expression = "java( DateTimeUtil.convertDateTimeToString(DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, comment.getModifiedDate()) )"),
    })
    public abstract UpdateCommentRspDTO toDTO(Comment comment);
}
