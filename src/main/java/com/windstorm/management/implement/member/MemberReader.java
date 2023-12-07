package com.windstorm.management.implement.member;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {
	private final MemberRepository memberRepository;

	public Member read(String uniqueMemberId) {
		return memberRepository.findByUniqueMemberId(uniqueMemberId)
			.orElseThrow(() -> new RuntimeException("교적번호: " + uniqueMemberId + "에 해당하는 데이터가 존재하지 않습니다."));
	}
}
