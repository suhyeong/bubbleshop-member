package com.bubbleshop.member.interfaces.event;

import com.bubbleshop.member.application.internal.commandservice.MemberCommandService;
import com.bubbleshop.member.domain.events.CreateMemberAuthoritySuccessEvent;
import com.bubbleshop.member.domain.model.aggregate.MemberLoginHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateMemberAuthoritySuccessEventListener {
    private final MemberCommandService memberCommandService;

    /**
     * 회원 권한 생성 (회원가입/로그인) 성공 이벤트 리스너
     * - 비동기 처리 & 신규 회원일 경우 INSERT 이후 처리를 위해 After Commit 처리
     *
     * 1. 최종 로그인 성공 이력 저장
     * 2. 신규 회원일 경우 쿠폰 발급 요청 TODO
     * @param event
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onSuccessCreateMemberAuthorityListener(CreateMemberAuthoritySuccessEvent event) {
        try {
            log.debug("onSuccessCreateMemberAuthorityListener");
            memberCommandService.saveMemberLoginHistory(new MemberLoginHistory(event));
        } catch (Exception e) {
            log.error("onSuccessCreateMemberAuthorityListener 회원 로그인 이후 후처리시 에러 발생, ", e);
        }
    }
}
