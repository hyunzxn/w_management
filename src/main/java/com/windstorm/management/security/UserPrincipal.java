package com.windstorm.management.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.windstorm.management.domain.member.Member;

public class UserPrincipal extends User {

	private final String uniqueMemberId;

	public UserPrincipal(Member member) {
		super(
			member.getUniqueMemberId(),
			member.getPassword(),
			List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()))
		);
		this.uniqueMemberId = member.getUniqueMemberId();
	}

	public String getUniqueMemberId() {
		return uniqueMemberId;
	}
}
