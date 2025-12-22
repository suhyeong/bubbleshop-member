package com.bubbleshop.member.domain.repository;

import com.bubbleshop.member.domain.model.aggregate.Member;
import com.bubbleshop.member.infrastructure.jpa.MemberCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String>, MemberCustomRepository {
    Optional<Member> findByProviderId(String providerId);
}
