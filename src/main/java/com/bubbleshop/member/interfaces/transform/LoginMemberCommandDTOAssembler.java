package com.bubbleshop.member.interfaces.transform;

import com.bubbleshop.member.domain.command.LoginMemberCommand;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberRspDTO;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class LoginMemberCommandDTOAssembler {

    @Mapping(target="password", source = "reqDTO.password")
    public abstract LoginMemberCommand toCommand(String id, LoginMemberReqDTO reqDTO);

    public abstract LoginMemberRspDTO toDTO(String accessToken);
}
