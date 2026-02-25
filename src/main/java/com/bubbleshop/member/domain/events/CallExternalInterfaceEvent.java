package com.bubbleshop.member.domain.events;

import com.bubbleshop.member.infrastructure.constants.ExternalInterfaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CallExternalInterfaceEvent {
    private ExternalInterfaceType requestType;

    // 회원 아이디 (nullable)
    private String memberId;

    // 요청
    private String requestUrl;
    private LocalDateTime requestDate;

    // 응답
    private String responseCode;
    private String responseMessage;
    private LocalDateTime responseDate;

    public CallExternalInterfaceEvent(ExternalInterfaceType requestType) {
        this.requestType = requestType;
    }
}
