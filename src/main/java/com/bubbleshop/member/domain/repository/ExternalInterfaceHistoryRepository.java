package com.bubbleshop.member.domain.repository;

import com.bubbleshop.member.domain.model.aggregate.ExternalInterfaceHistory;
import com.bubbleshop.member.domain.model.aggregate.MemberLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalInterfaceHistoryRepository extends JpaRepository<ExternalInterfaceHistory, Long> {
}
