package com.windstorm.management.implement.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.windstorm.management.controller.admin.member.request.MemberModify;
import com.windstorm.management.controller.member.request.PasswordModify;
import com.windstorm.management.domain.member.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberModifier {
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void modifyPassword(Member member, PasswordModify request) {
		member.modifyPassword(request);
		member.encodePassword(passwordEncoder);
	}

	@Transactional
	public void modify(Member member, MemberModify request) {
		member.modify(request);
	}
}
