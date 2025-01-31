package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.member.application.internal.commandservice.CommentCommandService;
import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.interfaces.rest.dto.UpdateCommentContentReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.UpdateCommentContentRspDTO;
import com.bubbleshop.member.interfaces.transform.UpdateCommentContentDTOAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bubbleshop.constants.StaticHeaders.BACKOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.member.interfaces.rest.controller.CommentUrl.COMMENT;
import static com.bubbleshop.member.interfaces.rest.controller.CommentUrl.COMMENT_DEFAULT_URL;

@Tag(name = "Comment Management API", description = "코멘트 (댓글) 백오피스 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = COMMENT_DEFAULT_URL, headers = BACKOFFICE_CHANNEL_HEADER)
public class CommentManagementController extends BaseController {
    private final CommentCommandService commentCommandService;

    private final UpdateCommentContentDTOAssembler updateCommentContentDTOAssembler;

    @Operation(summary = "코멘트 (댓글) 수정 API", description = "코멘트 번호로 코멘트 내용을 수정한다.")
    @PatchMapping(value = COMMENT)
    public ResponseEntity<Object> updateCommentContents(@PathVariable String commentNo,
                                                      @RequestBody UpdateCommentContentReqDTO request) {
        Comment comment = commentCommandService.updateCommentContents(commentNo, request.getContent());
        UpdateCommentContentRspDTO response = updateCommentContentDTOAssembler.toDTO(comment);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(response);
    }
}
