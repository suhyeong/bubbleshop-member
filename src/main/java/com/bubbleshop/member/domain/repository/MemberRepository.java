package com.bubbleshop.member.domain.repository;

import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.infrastructure.jpa.MemberCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String>, MemberCustomRepository {
}
