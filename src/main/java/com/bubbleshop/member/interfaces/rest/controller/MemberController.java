package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.member.application.internal.commandservice.MemberCommandService;
import com.bubbleshop.member.interfaces.rest.dto.LoginMemberReqDTO;
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

    private final MemberCommandService memberCommandService;


    @Operation(summary = "회원 생성 API", description = "회원가입을 수행한다.")
    @PostMapping(value = MEMBERS)
    public ResponseEntity<Void> createMember() {

        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @Operation(summary = "회원 로그인 API", description = "로그인을 수행한다.")
    @PostMapping(value = MEMBER)
    public ResponseEntity<Void> loginMember(@PathVariable String memberId, @RequestBody LoginMemberReqDTO loginMemberReqDTO) {
        memberCommandService.login(loginMemberCommandDTOAssembler.toCommand(memberId, loginMemberReqDTO));
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }
}
