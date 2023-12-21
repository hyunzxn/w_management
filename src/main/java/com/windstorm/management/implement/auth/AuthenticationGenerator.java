package com.windstorm.management.implement.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.windstorm.management.api.user.auth.request.Login;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationGenerator {
	private final AuthenticationManager authenticationManager;

	public Authentication authenticate(Login request) {
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(request.uniqueMemberId(), request.password());

		return authenticationManager.authenticate(authenticationToken);
	}

}
