package com.bubbleshop.member.interfaces.event;

import com.bubbleshop.member.application.internal.commandservice.ExternalInterfaceHistoryCommandService;
import com.bubbleshop.member.domain.events.CallExternalInterfaceEvent;
import com.bubbleshop.member.domain.model.aggregate.ExternalInterfaceHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CallExternalInterfaceEventListener {
    private final ExternalInterfaceHistoryCommandService externalInterfaceHistoryCommandService;

    @Async
    @EventListener
    public void onCallExternalInterfaceEventListener(CallExternalInterfaceEvent event) {
        try {
            log.debug("onCallExternalInterfaceEventListener");
            externalInterfaceHistoryCommandService.saveExternalInterfaceHistory(new ExternalInterfaceHistory(event));
        } catch (Exception e) {
            log.error("onCallExternalInterfaceEventListener 외부 인터페이스 호출 이벤트 처리시 에러 발생, ", e);
        }
    }
}
