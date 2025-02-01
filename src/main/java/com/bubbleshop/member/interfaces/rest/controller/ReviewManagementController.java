package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.member.application.internal.commandservice.ReviewCommandService;
import com.bubbleshop.member.application.internal.queryservice.ReviewQueryService;
import com.bubbleshop.member.domain.command.GetReviewListCommand;
import com.bubbleshop.member.domain.command.UpdateReviewCommand;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.model.view.ReviewListView;
import com.bubbleshop.member.domain.model.view.ReviewView;
import com.bubbleshop.member.interfaces.rest.dto.GetReviewDetailRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.GetReviewListRspDTO;
import com.bubbleshop.member.interfaces.rest.dto.UpdateReviewReqDTO;
import com.bubbleshop.member.interfaces.rest.dto.UpdateReviewRspDTO;
import com.bubbleshop.member.interfaces.transform.GetReviewDetailCommandDTOAssembler;
import com.bubbleshop.member.interfaces.transform.GetReviewListCommandDTOAssembler;
import com.bubbleshop.member.interfaces.transform.UpdateReviewDetailCommandDTOAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bubbleshop.constants.StaticHeaders.BACKOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.member.interfaces.rest.controller.ReviewUrl.*;

@Tag(name = "Review Management API", description = "리뷰 백오피스 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = REVIEW_DEFAULT_URL, headers = BACKOFFICE_CHANNEL_HEADER)
public class ReviewManagementController extends BaseController {
    private final GetReviewListCommandDTOAssembler getReviewListCommandDTOAssembler;
    private final GetReviewDetailCommandDTOAssembler getReviewDetailCommandDTOAssembler;
    private final UpdateReviewDetailCommandDTOAssembler updateReviewDetailCommandDTOAssembler;

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    @Operation(summary = "리뷰 리스트 조회 API", description = "리뷰 리스트를 페이징 처리하여 조회한다.")
    @GetMapping(value = REVIEWS)
    public ResponseEntity<Object> getReviewList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "1") Integer size,
                                                @RequestParam(required = false) String memberId,
                                                @RequestParam(required = false) String productCode,
                                                @RequestParam(required = false, defaultValue = "") String createdStartDate,
                                                @RequestParam(required = false, defaultValue = "") String createdEndDate) {
        GetReviewListCommand command = getReviewListCommandDTOAssembler.toCommand(page, size, memberId, productCode, createdStartDate, createdEndDate);
        ReviewListView view = reviewQueryService.getReviewList(command);
        GetReviewListRspDTO response = getReviewListCommandDTOAssembler.toDTO(view);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(response);
    }

    @Operation(summary = "리뷰 상세 조회 API", description = "리뷰 번호로 리뷰 상세 정보를 조회한다.")
    @GetMapping(value = REVIEW)
    public ResponseEntity<Object> getReviewDetail(@PathVariable String reviewNo) {
        ReviewView review = reviewQueryService.getReviewDetail(reviewNo);
        GetReviewDetailRspDTO response = getReviewDetailCommandDTOAssembler.toDTO(review);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(response);
    }

    @Operation(summary = "리뷰 상세 정보 수정 API", description = "리뷰 번호로 리뷰 상세 정보를 수정한다.")
    @PatchMapping(value = REVIEW)
    public ResponseEntity<Object> updateReviewDetail(@PathVariable String reviewNo,
                                                     @RequestBody UpdateReviewReqDTO request) {
        UpdateReviewCommand command = updateReviewDetailCommandDTOAssembler.toCommand(reviewNo, request);
        Review review = reviewCommandService.updateReviewDetail(command);
        UpdateReviewRspDTO response = updateReviewDetailCommandDTOAssembler.toDTO(review);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(response);
    }
}
