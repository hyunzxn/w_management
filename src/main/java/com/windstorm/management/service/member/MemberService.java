package com.windstorm.management.service.member;

import org.springframework.stereotype.Service;

import com.windstorm.management.controller.member.request.PasswordModify;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberModifier;
import com.windstorm.management.implement.member.MemberReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberReader memberReader;
	private final MemberModifier memberModifier;

	public void modify(String uniqueId, PasswordModify request) {
		Member member = memberReader.read(uniqueId);
		memberModifier.modifyPassword(member, request);
	}
}
