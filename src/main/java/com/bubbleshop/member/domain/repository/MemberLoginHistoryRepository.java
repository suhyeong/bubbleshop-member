package com.bubbleshop.member.domain.repository;

import com.bubbleshop.member.domain.model.aggregate.MemberLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLoginHistoryRepository extends JpaRepository<MemberLoginHistory, Long> {
}
