package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.CreateCommentCommand;
import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.interfaces.rest.dto.CreateCommentReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.CreateCommentRspDTO;
import com.bubbleshop.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {DateTimeUtil.class})
public abstract class CreateCommentCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "content", source = "request.content"),
            @Mapping(target = "createdBy", source = "userId")
    })
    public abstract CreateCommentCommand toCommand(CreateCommentReqDTO request, String userId);

    @Mappings({
            @Mapping(target = "content", source = "commentContent"),
            @Mapping(target = "createdDate", expression = "java( DateTimeUtil.convertDateTimeToString(DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, comment.getCreatedDate()) )"),
            @Mapping(target = "modifiedDate", expression = "java( DateTimeUtil.convertDateTimeToString(DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, comment.getModifiedDate()) )")
    })
    public abstract CreateCommentRspDTO toDTO(Comment comment);
}
