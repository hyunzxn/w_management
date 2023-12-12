package com.windstorm.management.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windstorm.management.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

	Optional<Member> findByUniqueId(String uniqueId);
}
