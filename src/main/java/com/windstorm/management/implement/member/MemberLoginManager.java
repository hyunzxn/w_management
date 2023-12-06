package com.windstorm.management.implement.member;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.member.request.MemberLogin;
import com.windstorm.management.domain.member.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberLoginManager {

	private final MemberReader memberReader;

	@Transactional
	public Member login(MemberLogin request) {
		Member member = memberReader.read(request.uniqueMemberId());

		if (!member.getPassword().equals(request.password())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return member;
	}
}
