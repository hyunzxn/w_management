package com.windstorm.management.service.member;

import org.springframework.stereotype.Service;

import com.windstorm.management.controller.member.request.MemberCreate;
import com.windstorm.management.controller.member.request.MemberLogin;
import com.windstorm.management.controller.member.response.MemberResponse;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberAppender;
import com.windstorm.management.implement.member.MemberLoginManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberAppender memberAppender;
	private final MemberLoginManager loginManager;

	public MemberResponse create(MemberCreate request) {
		Member newMember = memberAppender.append(request);
		return MemberResponse.toResponse(newMember);
	}

	public MemberResponse login(MemberLogin request) {
		Member member = loginManager.login(request);
		return MemberResponse.toResponse(member);
	}
}
