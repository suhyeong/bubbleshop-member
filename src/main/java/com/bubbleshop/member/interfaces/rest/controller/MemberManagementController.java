package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.member.application.internal.queryservice.MemberQueryService;
import com.bubbleshop.member.domain.command.GetMemberListCommand;
import com.bubbleshop.member.domain.model.view.MemberListView;
import com.bubbleshop.member.interfaces.rest.dto.GetMemberListRspDTO;
import com.bubbleshop.member.interfaces.transform.GetMemberListCommandDTOAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bubbleshop.constants.StaticHeaders.BACKOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.member.interfaces.rest.controller.MemberUrl.*;

@Tag(name = "Member Management API", description = "회원 백오피스 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = MEMBER_DEFAULT_URL, headers = BACKOFFICE_CHANNEL_HEADER)
public class MemberManagementController extends BaseController {

    private final GetMemberListCommandDTOAssembler getMemberListCommandDTOAssembler;

    private final MemberQueryService memberQueryService;

    @Operation(summary = "회원 리스트 조회 API", description = "상품 리스트를 페이징 처리하여 조회한다.")
    @GetMapping(value = MEMBERS)
    public ResponseEntity<Object> getMemberList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(required = false, defaultValue = "1") Integer size,
                                                 @RequestParam(required = false) String memberId,
                                                 @RequestParam(required = false) String memberNickname,
                                                 @RequestParam(required = false, defaultValue = "false") boolean isMemberNicknameContains,
                                                 @RequestParam String joinStartDate,
                                                 @RequestParam String joinEndDate) {
        GetMemberListCommand command = getMemberListCommandDTOAssembler.toCommand(page, size, memberId, memberNickname, isMemberNicknameContains, joinStartDate, joinEndDate);
        MemberListView view = memberQueryService.getMemberList(command);
        GetMemberListRspDTO response = getMemberListCommandDTOAssembler.toDTO(view);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(response);
    }

    @Operation(summary = "회원 상세 정보 조회 API", description = "회원 아이디로 회원 상세 정보를 조회한다.")
    @GetMapping(value = MEMBER)
    public ResponseEntity<Object> getMember(@PathVariable String memberId) {

        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .build(); // todo body
    }
}
