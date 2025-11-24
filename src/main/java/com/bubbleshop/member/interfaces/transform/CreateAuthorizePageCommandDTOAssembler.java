package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.CreateAuthorizePageCommand;
import com.bubbleshop.member.domain.constant.MemberAccessActionType;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.valueobjects.AuthorizePageInfo;
import com.bubbleshop.member.interfaces.rest.dto.CreateAuthorizePageRspDTO;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {MemberAccessActionType.class, MemberProviderType.class})
public abstract class CreateAuthorizePageCommandDTOAssembler {

    @Mapping(target="accessActionType", expression = " java( MemberAccessActionType.find(accessType) ) ")
    @Mapping(target="providerType", expression = " java( MemberProviderType.findPath(providerType) ) ")
    public abstract CreateAuthorizePageCommand toCommand(String accessType, String providerType);

    public abstract CreateAuthorizePageRspDTO toDTO(AuthorizePageInfo info);
}
