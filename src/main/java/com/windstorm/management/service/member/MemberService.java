package com.windstorm.management.service.member;

import java.util.List;

import org.springframework.stereotype.Service;

import com.windstorm.management.controller.admin.member.request.MemberModify;
import com.windstorm.management.controller.member.request.PasswordModify;
import com.windstorm.management.controller.member.response.MemberResponse;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberModifier;
import com.windstorm.management.implement.member.MemberReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberReader memberReader;
	private final MemberModifier memberModifier;

	public List<MemberResponse> get(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("이름을 입력해주세요.");
		}
		List<Member> members = memberReader.readByName(name);
		return members.stream()
			.map(MemberResponse::toResponse)
			.toList();
	}

	public void modifyPassword(String uniqueId, PasswordModify request) {
		Member member = memberReader.read(uniqueId);
		memberModifier.modifyPassword(member, request);
	}

	public MemberResponse modify(String uniqueId, MemberModify request) {
		if (uniqueId.isEmpty()) {
			throw new IllegalArgumentException("교적번호가 입력되지 않았습니다.");
		}
		Member member = memberReader.read(uniqueId);
		memberModifier.modify(member, request);
		return MemberResponse.toResponse(member);
	}
}
