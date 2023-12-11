package com.windstorm.management.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.windstorm.management.domain.member.Member;

import lombok.Getter;

@Getter
public class UserPrincipal extends User {

	private final String uniqueId;

	public UserPrincipal(Member member) {
		super(
			member.getUniqueId(),
			member.getPassword(),
			List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()))
		);
		this.uniqueId = member.getUniqueId();
	}
}
