package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.member.domain.events.CallExternalInterfaceEvent;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "external_interface_history")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ExternalInterfaceHistory implements Serializable {
    @Serial
    private static final long serialVersionUID = -5464512673849473488L;

    @Id
    @Description("외부 인터페이스 호출 이력 순번")
    @Column(name = "ext_intf_hist_sn")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Description("외부 인터페이스 타입")
    @Column(name = "ext_intf_tp", nullable = false)
    private String type;

    @Description("회원 아이디")
    @Column(name = "member_id")
    private String memberId;

    @Description("외부 인터페이스 호출 요청 URL")
    @Column(name = "ext_intf_req_url", nullable = false)
    private String requestUrl;

    @Description("외부 인터페이스 호출 요청일시")
    @Column(name = "ext_intf_req_dt", nullable = false)
    private LocalDateTime requestDate;

    @Description("외부 인터페이스 응답 결과코드")
    @Column(name = "ext_intf_rsp_cd", nullable = false)
    private String responseCode;

    @Description("외부 인터페이스 응답 결과메세지")
    @Column(name = "ext_intf_rsp_msg", nullable = false)
    private String responseMessage;

    @Description("외부 인터페이스 응답일시")
    @Column(name = "ext_intf_rsp_dt", nullable = false)
    private LocalDateTime responseDate;

    @Description("생성 일시")
    @Column(name = "crt_dt", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    public ExternalInterfaceHistory(CallExternalInterfaceEvent event) {
        this.type = event.getRequestType().name();
        this.memberId = event.getMemberId();
        this.requestUrl = event.getRequestUrl();
        this.requestDate = event.getRequestDate();
        this.responseCode = event.getResponseCode();
        this.responseMessage = event.getResponseMessage();
        this.responseDate = event.getResponseDate();
    }
}
