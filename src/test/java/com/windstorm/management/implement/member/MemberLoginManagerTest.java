package com.windstorm.management.implement.member;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.windstorm.management.api.user.auth.request.Login;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.auth.AuthenticationGenerator;
import com.windstorm.management.infrastructure.security.UserPrincipal;
import com.windstorm.management.infrastructure.security.jwt.JwtProvider;
import com.windstorm.management.infrastructure.security.jwt.JwtResponse;

@ExtendWith(MockitoExtension.class)
class MemberLoginManagerTest {
	@Mock
	private MemberReader memberReader;

	@Mock
	private JwtProvider jwtProvider;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private AuthenticationGenerator authenticationGenerator;

	@InjectMocks
	private MemberLoginManager memberLoginManager;

	@Test
	@DisplayName("로그인을 할 수 있다.")
	void loginSuccess() {
		// given
		Login request = Login.builder()
			.uniqueMemberId("1")
			.password("saeeden1234!!")
			.build();
		Member member = createMember();
		Authentication authentication = createAuthentication();
		JwtResponse jwt = JwtResponse.builder()
			.grantType("Bearer")
			.accessToken("aaaaa")
			.refreshToken("bbbbb")
			.build();

		when(memberReader.read(any(String.class))).thenReturn(member);
		when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
		when(authenticationGenerator.authenticate(any(Login.class))).thenReturn(authentication);
		when(jwtProvider.generateToken(any(Authentication.class))).thenReturn(jwt);

		// when
		JwtResponse result = memberLoginManager.login(request);

		// then
		assertThat(result.getGrantType()).isEqualTo("Bearer");
		assertThat(result.getAccessToken()).isEqualTo("aaaaa");
		assertThat(result.getRefreshToken()).isEqualTo("bbbbb");
	}

	@Test
	@DisplayName("비밀번호가 일치하지 않으면 로그인을 할 수 없다.")
	void loginFail() {
		// given
		Login request = Login.builder()
			.uniqueMemberId("1")
			.password("saeeden1234!!")
			.build();
		Member member = createMember();

		when(memberReader.read(any(String.class))).thenReturn(member);
		when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

		// when, then
		assertThatThrownBy(() -> memberLoginManager.login(request))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("비밀번호가 일치하지 않습니다.");
	}

	private Member createMember() {
		return Member.builder()
			.uniqueId("1")
			.name("홍길동")
			.password("saeeden1234!!")
			.birthDate(LocalDate.of(2000, 9, 25))
			.division(Division.DANIEL)
			.gender(Gender.MALE)
			.role(LeaderRole.CAPTAIN)
			.phoneNumber("010-1234-5678")
			.address("경기도 성남시")
			.build();
	}

	private Authentication createAuthentication() {
		UserDetails userDetails = new UserPrincipal(createMember());
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}