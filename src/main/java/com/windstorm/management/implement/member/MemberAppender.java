package com.windstorm.management.implement.member;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.member.request.MemberCreate;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberAppender {

	private final MemberRepository memberRepository;

	@Transactional
	public Member append(MemberCreate request) {
		try {
			Member newMember = Member.create(
				request.uniqueMemberId(),
				request.name(),
				request.name(),
				request.birthDate(),
				request.division(),
				request.gender(),
				request.role(),
				request.phoneNumber(),
				request.address()
			);
			return memberRepository.save(newMember);
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException("교적번호: " + request.uniqueMemberId() + "에 해당하는 데이터가 이미 존재합니다.", e);
		}
	}
}
