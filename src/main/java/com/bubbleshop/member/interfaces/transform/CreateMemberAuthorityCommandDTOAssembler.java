package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.CreateMemberAuthorityCommand;
import com.bubbleshop.member.domain.constant.MemberAccessActionType;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.interfaces.rest.dto.CreateMemberAuthorityReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CreateMemberAuthorityCommandDTOAssembler {
    @Mapping(target="accessActionType", ignore = true)
    @Mapping(target="providerType", ignore = true)
    public abstract CreateMemberAuthorityCommand toCommand(String requestId, CreateMemberAuthorityReqDTO reqDTO);

    @AfterMapping
    protected void afterMappingToCommand(@MappingTarget CreateMemberAuthorityCommand.CreateMemberAuthorityCommandBuilder builder,
                                         String requestId, CreateMemberAuthorityReqDTO reqDTO) {
        String accessTypeCode = reqDTO.getState().substring(0,1);
        MemberAccessActionType actionType = MemberAccessActionType.find(accessTypeCode);

        builder.accessActionType(actionType)
                .providerType(MemberProviderType.findPath(reqDTO.getProvider()))
                .build();
    }

}
