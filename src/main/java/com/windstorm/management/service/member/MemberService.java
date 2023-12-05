package com.windstorm.management.service.member;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.member.request.MemberCreate;
import com.windstorm.management.controller.member.response.MemberResponse;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public MemberResponse create(MemberCreate request) {
		Optional<Member> findResult = memberRepository.findByUniqueMemberId(request.uniqueMemberId());
		if (findResult.isPresent()) {
			throw new DataIntegrityViolationException("교적번호 " + request.uniqueMemberId() + "에 해당하는 데이터가 이미 존재합니다.");
		}

		Member member = Member.create(
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

		return MemberResponse.toResponse(memberRepository.save(member));
	}
}
