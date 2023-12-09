package com.windstorm.management.implement.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.auth.request.Login;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.auth.AuthenticationGenerator;
import com.windstorm.management.security.jwt.JwtProvider;
import com.windstorm.management.security.jwt.JwtResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberLoginManager {

	private final MemberReader memberReader;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationGenerator authenticationGenerator;

	@Transactional
	public JwtResponse login(Login request) {
		Member member = memberReader.read(request.uniqueMemberId());

		if (!passwordEncoder.matches(request.password(), member.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		Authentication authentication = authenticationGenerator.authenticate(request);

		return jwtProvider.generateToken(authentication);
	}
}
