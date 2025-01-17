package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.GetMemberListCommand;
import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.domain.model.view.MemberListView;
import com.bubbleshop.member.domain.model.view.MemberView;
import com.bubbleshop.member.interfaces.rest.dto.GetMemberDetailRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.GetMemberListRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.GetMemberRspDTO;
import com.bubbleshop.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalTime;

import static com.bubbleshop.util.DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {DateTimeUtils.class})
public abstract class GetMemberListCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "pageable", ignore = true),
            @Mapping(target = "isNameContains", source = "isMemberNicknameContains"),
            @Mapping(target = "joinStartDate", ignore = true),
            @Mapping(target = "joinEndDate", ignore = true)
    })
    public abstract GetMemberListCommand toCommand(Integer page, Integer size,
                                                   String memberId, String memberNickname, boolean isMemberNicknameContains,
                                                   String joinStartDate, String joinEndDate);

    @AfterMapping
    protected void afterMappingToCommand(@MappingTarget GetMemberListCommand.GetMemberListCommandBuilder builder,
                                         Integer page, Integer size,
                                         String memberId, String memberNickname, boolean isMemberNicknameContains,
                                         String joinStartDate, String joinEndDate) {
        builder.pageable(PageRequest.of(page-1, size));
        builder.joinStartDate(DateTimeUtils.convertStringToDateTime(DATE_FORMAT_YYYY_MM_DD_DASH, joinStartDate, LocalTime.MIN));
        builder.joinEndDate(DateTimeUtils.convertStringToDateTime(DATE_FORMAT_YYYY_MM_DD_DASH, joinEndDate, LocalTime.MAX));
    }

    @Mappings({
            @Mapping(target = "memberList", qualifiedByName = "GetMemberListRspDTO.MemberRspDTO")
    })
    public abstract GetMemberListRspDTO toDTO(MemberListView memberListView);

    @Named("GetMemberListRspDTO.MemberRspDTO")
    @Mappings({
            @Mapping(target = "joinDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, memberView.getJoinDate()) )"),
            @Mapping(target = "withdrawalDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, memberView.getWithdrawalDate()) )")
    })
    public abstract GetMemberRspDTO toMemberDTO(MemberView memberView);

    @Mappings({
            @Mapping(target = "joinDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, member.getJoinDate()) )"),
            @Mapping(target = "withdrawalDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, member.getWithdrawalDate()) )"),
            @Mapping(target = "birthDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH, member.getBirthDate()) )"),
            @Mapping(target = "email", source = "memberEmailInfo.email"),
            @Mapping(target = "isEmailReceiveAgree", source = "memberEmailInfo.emailReceiveAgree"),
            @Mapping(target = "leftDateToDiscardMemberInfo", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT, member.getLeftDateToDiscardMemberInfo()) )"),
    })
    public abstract GetMemberDetailRspDTO toDTO(Member member);
}
