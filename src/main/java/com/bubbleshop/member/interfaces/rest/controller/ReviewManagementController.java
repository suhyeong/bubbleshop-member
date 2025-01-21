package com.bubbleshop.member.interfaces.rest.controller;

import com.bubbleshop.member.application.internal.queryservice.ReviewQueryService;
import com.bubbleshop.member.domain.command.GetReviewListCommand;
import com.bubbleshop.member.domain.model.view.ReviewListView;
import com.bubbleshop.member.interfaces.rest.dto.GetReviewListRspDTO;
import com.bubbleshop.member.interfaces.transform.GetReviewListCommandDTOAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.bubbleshop.constants.StaticHeaders.BACKOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.member.interfaces.rest.controller.ReviewUrl.REVIEWS;
import static com.bubbleshop.member.interfaces.rest.controller.ReviewUrl.REVIEW_DEFAULT_URL;

@Tag(name = "Review Management API", description = "리뷰 백오피스 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = REVIEW_DEFAULT_URL, headers = BACKOFFICE_CHANNEL_HEADER)
public class ReviewManagementController extends BaseController {
    private final GetReviewListCommandDTOAssembler getReviewListCommandDTOAssembler;
    private final ReviewQueryService reviewQueryService;

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
}
