package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.config.jwt.TokenView;
import com.bubbleshop.config.jwt.RequestId;
import com.bubbleshop.member.application.internal.commandservice.MemberCommandService;
import com.bubbleshop.member.domain.model.valueobjects.AuthorizePageInfo;
import com.bubbleshop.member.interfaces.rest.dto.CreateAuthorizePageRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.CreateMemberAuthorityReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.GetAuthorityRoleRspDTO;
import com.bubbleshop.member.interfaces.transform.CreateAuthorizePageCommandDTOAssembler;
import com.bubbleshop.member.interfaces.transform.CreateMemberAuthorityCommandDTOAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bubbleshop.constants.StaticHeaders.FRONTOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.constants.StaticValues.Token.*;
import static com.bubbleshop.member.interfaces.rest.controller.MemberUrl.*;

@Tag(name = "Member API", description = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = MEMBER_DEFAULT_URL, headers = FRONTOFFICE_CHANNEL_HEADER)
public class MemberController extends BaseController {

    private final CreateAuthorizePageCommandDTOAssembler createAuthorizePageCommandDTOAssembler;
    private final CreateMemberAuthorityCommandDTOAssembler createMemberAuthorityCommandDTOAssembler;

    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원가입/로그인 진행 URL 생성 API", description = "회원가입/로그인시 진행시 이후 URL 을 생성한다.")
    @PostMapping(value = AUTH_PAGE)
    public ResponseEntity<CreateAuthorizePageRspDTO> createAuthorizePage(
            @RequestId String requestId, @PathVariable String provider, @RequestParam(name = "accessType") String accessType) {
        AuthorizePageInfo info = memberCommandService.createAuthorizePage(
                createAuthorizePageCommandDTOAssembler.toCommand(requestId, accessType, provider));
        CreateAuthorizePageRspDTO response = createAuthorizePageCommandDTOAssembler.toDTO(info);
        return ResponseEntity.ok().headers(getSuccessHeaders()).body(response);
    }

    @Operation(summary = "회원 회원가입/로그인 API", description = "회원의 회원가입/로그인을 수행한다.")
    @PostMapping(value = AUTH)
    public ResponseEntity<Void> createMemberAuthority(@RequestId String requestId, HttpServletResponse response, @RequestBody @Valid CreateMemberAuthorityReqDTO createMemberAuthorityReqDTO) {
        TokenView view = memberCommandService.createMemberAuthority(createMemberAuthorityCommandDTOAssembler.toCommand(requestId, createMemberAuthorityReqDTO));
        setTokenToCookie(response, ACCESS_TOKEN, view.getAccessToken(), view.getAccessTokenExpirationTime());
        setTokenToCookie(response, REFRESH_TOKEN, view.getRefreshToken(), view.getRefreshTokenExpirationTime());
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @Operation(summary = "회원 로그아웃 API", description = "회원의 로그아웃을 수행한다.")
    @DeleteMapping(value = AUTH)
    public ResponseEntity<Void> deleteMemberAuthority(HttpServletResponse response) {
        TokenView view = memberCommandService.deleteMemberAuthority();
        setTokenToCookie(response, ACCESS_TOKEN, view.getAccessToken(), view.getAccessTokenExpirationTime());
        setTokenToCookie(response, REFRESH_TOKEN, view.getRefreshToken(), view.getRefreshTokenExpirationTime());
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @Operation(summary = "회원 토큰 롤 조회 API", description = "토큰으로 회원/비회원 롤 정보를 조회한다.")
    @GetMapping(value = AUTH)
    public ResponseEntity<GetAuthorityRoleRspDTO> getAuthorityRole() {
        boolean isMember = memberCommandService.isAuthorityMemberRole();
        GetAuthorityRoleRspDTO response = new GetAuthorityRoleRspDTO(isMember);
        return ResponseEntity.ok().headers(getSuccessHeaders()).body(response);
    }
}
