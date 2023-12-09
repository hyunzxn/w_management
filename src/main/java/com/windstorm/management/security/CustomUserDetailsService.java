package com.windstorm.management.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberReader memberReader;

	@Override
	public UserDetails loadUserByUsername(String uniqueMemberId) throws UsernameNotFoundException {
		Member member = memberReader.read(uniqueMemberId);
		return new UserPrincipal(member);
	}
}
