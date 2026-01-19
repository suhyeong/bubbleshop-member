package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.UpdateReviewCommand;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.interfaces.rest.dto.UpdateReviewReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.UpdateReviewRspDTO;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UpdateReviewDetailCommandDTOAssembler {
    public abstract UpdateReviewCommand toCommand(String reviewNo, UpdateReviewReqDTO request);

    @Mappings({
            @Mapping(target = "isReviewShow", source = "show")
    })
    public abstract UpdateReviewRspDTO toDTO(Review review);
}
