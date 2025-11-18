package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.CreateLoginAuthorizeCommand;
import com.bubbleshop.member.domain.command.LoginMemberCommand;
import com.bubbleshop.member.domain.constant.MemberAccessActionType;
import com.bubbleshop.member.domain.constant.MemberProviderType;
import com.bubbleshop.member.domain.model.valueobjects.LoginAuthorizeInfo;
import com.bubbleshop.member.interfaces.rest.dto.CreateLoginAuthorizeRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberRspDTO;
import com.bubbleshop.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {MemberAccessActionType.class, MemberProviderType.class})
public abstract class CreateLoginAuthorizeCommandDTOAssembler {

    @Mapping(target="accessActionType", expression = " java( MemberAccessActionType.find(accessType) ) ")
    @Mapping(target="providerType", expression = " java( MemberProviderType.findPath(providerType) ) ")
    public abstract CreateLoginAuthorizeCommand toCommand(String accessType, String providerType);

    public abstract CreateLoginAuthorizeRspDTO toDTO(LoginAuthorizeInfo info);
}
