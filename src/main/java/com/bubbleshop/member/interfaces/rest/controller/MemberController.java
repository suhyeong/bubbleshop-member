package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.member.application.internal.commandservice.MemberCommandService;
import com.bubbleshop.member.domain.model.valueobjects.AuthorizePageInfo;
import com.bubbleshop.member.interfaces.rest.dto.CreateAuthorizePageRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.CreateLoginStateRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberRspDTO;
import com.bubbleshop.member.interfaces.transform.CreateAuthorizePageCommandDTOAssembler;
import com.bubbleshop.member.interfaces.transform.LoginMemberCommandDTOAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bubbleshop.constants.StaticHeaders.FRONTOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.member.interfaces.rest.controller.MemberUrl.*;

@Tag(name = "Member API", description = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = MEMBER_DEFAULT_URL, headers = FRONTOFFICE_CHANNEL_HEADER)
public class MemberController extends BaseController {

    private final LoginMemberCommandDTOAssembler loginMemberCommandDTOAssembler;
    private final CreateAuthorizePageCommandDTOAssembler createAuthorizePageCommandDTOAssembler;

    private final MemberCommandService memberCommandService;


    @Operation(summary = "회원 생성 API", description = "회원가입을 수행한다.")
    @PostMapping(value = MEMBERS)
    public ResponseEntity<Void> createMember() {

        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @Operation(summary = "네이버 회원가입/로그인시 필요한 상태 토큰 생성 API", description = "네이버 로그인시 필요한 상태 토큰값을 생성한다.")
    @PostMapping(value = AUTH_STATE)
    public ResponseEntity<CreateLoginStateRspDTO> createLoginState() {

        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @Operation(summary = "회원가입/로그인 진행 URL 생성 API", description = "회원가입/로그인시 진행시 이후 URL 을 생성한다.")
    @PostMapping(value = AUTH_PAGE)
    public ResponseEntity<CreateAuthorizePageRspDTO> createAuthorizePage(
            @PathVariable String provider, @RequestParam(name = "accessType") String accessType) {
        AuthorizePageInfo info = memberCommandService.createAuthorizePage(
                createAuthorizePageCommandDTOAssembler.toCommand(accessType, provider));
        CreateAuthorizePageRspDTO response = createAuthorizePageCommandDTOAssembler.toDTO(info);
        return ResponseEntity.ok().headers(getSuccessHeaders()).body(response);
    }

    @Operation(summary = "회원 로그인 API", description = "로그인을 수행한다.")
    @PostMapping(value = MEMBER)
    public ResponseEntity<LoginMemberRspDTO> loginMember(@PathVariable String memberId, @RequestBody LoginMemberReqDTO loginMemberReqDTO) {
        String accessToken = memberCommandService.login(loginMemberCommandDTOAssembler.toCommand(memberId, loginMemberReqDTO));
        LoginMemberRspDTO response = loginMemberCommandDTOAssembler.toDTO(accessToken);
        return ResponseEntity.ok().headers(getSuccessHeaders()).body(response);
    }
}
