package com.windstorm.management.service.auth;

import org.springframework.stereotype.Service;

import com.windstorm.management.api.user.auth.request.Login;
import com.windstorm.management.api.user.auth.request.Signup;
import com.windstorm.management.api.user.member.response.MemberResponse;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberAppender;
import com.windstorm.management.implement.member.MemberLoginManager;
import com.windstorm.management.infrastructure.security.jwt.JwtResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberAppender memberAppender;
	private final MemberLoginManager loginManager;

	public MemberResponse create(Signup request) {
		Member newMember = memberAppender.append(request);
		return MemberResponse.toResponse(newMember);
	}

	public JwtResponse login(Login request) {
		return loginManager.login(request);
	}

	public void logout(String accessToken) {
		loginManager.logout(accessToken);
	}
}
