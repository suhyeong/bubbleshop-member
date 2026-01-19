package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.CreateMemberAuthorityCommand;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.view.CreateMemberAuthorityView;
import com.bubbleshop.member.interfaces.rest.dto.CreateMemberAuthorityReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.CreateMemberAuthorityRspDTO;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, imports = { MemberProviderType.class })
public abstract class CreateMemberAuthorityCommandDTOAssembler {
    @Mapping(target="providerType", expression = " java( MemberProviderType.findPath(reqDTO.getProvider()) ) ")
    public abstract CreateMemberAuthorityCommand toCommand(String requestId, CreateMemberAuthorityReqDTO reqDTO);

    @Mapping(target = "isNewMember", source = "view.newMember")
    public abstract CreateMemberAuthorityRspDTO toRspDTO(CreateMemberAuthorityView view);
}
