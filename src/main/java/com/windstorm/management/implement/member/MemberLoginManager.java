package com.windstorm.management.implement.member;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.auth.request.Login;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.security.jwt.JwtToken;
import com.windstorm.management.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberLoginManager {

	private final MemberReader memberReader;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public JwtToken login(Login request) {
		Member member = memberReader.read(request.uniqueMemberId());

		if (!passwordEncoder.matches(request.password(), member.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(request.uniqueMemberId(), request.password());

		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		return jwtTokenProvider.generateToken(authentication);
	}
}
