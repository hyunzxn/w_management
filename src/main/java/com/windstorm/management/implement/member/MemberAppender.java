package com.windstorm.management.implement.member;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.auth.request.Signup;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberAppender {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Member append(Signup request) {
		try {
			Member newMember = Member.create(
				request.uniqueMemberId(),
				request.name(),
				"saeeden1234!!",
				request.birthDate(),
				request.division(),
				request.gender(),
				request.role(),
				request.phoneNumber(),
				request.address()
			);
			newMember.encodePassword(passwordEncoder);
			return memberRepository.save(newMember);
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException("교적번호: " + request.uniqueMemberId() + "에 해당하는 데이터가 이미 존재합니다.", e);
		}
	}
}
