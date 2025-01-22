package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.GetReviewListCommand;
import com.bubbleshop.member.domain.model.view.ReviewListView;
import com.bubbleshop.member.domain.model.view.ReviewView;
import com.bubbleshop.member.interfaces.rest.dto.GetReviewListRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.GetReviewRspDTO;
import com.bubbleshop.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalTime;

import static com.bubbleshop.util.DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {DateTimeUtils.class})
public abstract class GetReviewListCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "pageable", ignore = true),
            @Mapping(target = "createdStartDate", ignore = true),
            @Mapping(target = "createdEndDate", ignore = true)
    })
    public abstract GetReviewListCommand toCommand(Integer page, Integer size, String memberId, String productCode,
                                                   String createdStartDate, String createdEndDate);

    @AfterMapping
    protected void afterMappingToCommand(@MappingTarget GetReviewListCommand.GetReviewListCommandBuilder builder,
                                         Integer page, Integer size, String memberId, String productCode,
                                         String createdStartDate, String createdEndDate) {
        builder.pageable(PageRequest.of(page-1, size));
        builder.createdStartDate(DateTimeUtils.convertStringToDateTime(DATE_FORMAT_YYYY_MM_DD_DASH, createdStartDate, LocalTime.MIN));
        builder.createdEndDate(DateTimeUtils.convertStringToDateTime(DATE_FORMAT_YYYY_MM_DD_DASH, createdEndDate, LocalTime.MAX));
    }

    @Mappings({
            @Mapping(target = "reviewList", qualifiedByName = "GetReviewDetailRspDTO.ReviewRspDTO")
    })
    public abstract GetReviewListRspDTO toDTO(ReviewListView reviewListView);

    @Named("GetReviewDetailRspDTO.ReviewRspDTO")
    @Mapping(target = "createdDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, reviewView.getCreatedDate()) )")
    public abstract GetReviewRspDTO toReviewDTO(ReviewView reviewView);
}
