package com.bubbleshop.member.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMemberListCommand {
    private Pageable pageable;

    private String memberId;
    private String memberEmail;
    private boolean isEmailContains;
    private LocalDateTime joinStartDate;
    private LocalDateTime joinEndDate;
}
