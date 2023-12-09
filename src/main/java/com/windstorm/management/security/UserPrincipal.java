package com.windstorm.management.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.windstorm.management.domain.member.Member;

public class UserPrincipal extends User {

	private final Long memberId;

	public UserPrincipal(Member member) {
		super(
			member.getName(),
			member.getPassword(),
			List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()))
		);
		this.memberId = member.getId();
	}

	public Long getUserId() {
		return memberId;
	}
}
