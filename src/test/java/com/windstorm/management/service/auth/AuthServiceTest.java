package com.windstorm.management.service.auth;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.windstorm.management.api.user.auth.request.Login;
import com.windstorm.management.api.user.auth.request.Signup;
import com.windstorm.management.api.user.member.response.MemberResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberAppender;
import com.windstorm.management.implement.member.MemberLoginManager;
import com.windstorm.management.infrastructure.security.jwt.JwtResponse;
import com.windstorm.management.repository.member.MemberRepository;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {
	@Autowired
	private AuthService authService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@AfterEach
	void cleanUp() {
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("새로운 유저를 추가할 수 있다.")
	void signUpSuccess() {
		// given
		Signup request = Signup.builder()
			.uniqueMemberId("1")
			.name("홍길동")
			.birthDate(LocalDate.of(2000, 11, 10))
			.division(Division.DANIEL)
			.gender(Gender.MALE)
			.role(LeaderRole.CAPTAIN)
			.phoneNumber("010-1234-5676")
			.address("경기도 수원시")
			.build();

		// when
		MemberResponse result = authService.create(request);

		// then
		assertThat(result.uniqueMemberId()).isEqualTo("1");
		assertThat(result.name()).isEqualTo("홍길동");
		assertThat(result.division()).isEqualTo(Division.DANIEL);
	}

	@Test
	@DisplayName("동일한 uniqueMemberId의 유저는 추가할 수 없다.")
	void signUpFail() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		Signup request = Signup.builder()
			.uniqueMemberId("1")
			.name("홍길동")
			.birthDate(LocalDate.of(2000, 11, 10))
			.division(Division.DANIEL)
			.gender(Gender.MALE)
			.role(LeaderRole.CAPTAIN)
			.phoneNumber("010-2222-3333")
			.address("경기도 수원시")
			.build();

		// when, then
		assertThatThrownBy(() -> authService.create(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("교적번호: " + request.uniqueMemberId() + "에 해당하는 데이터가 이미 존재합니다.");
	}

	@Test
	@DisplayName("로그인을 할 수 있다.")
	void loginSuccess() {
		// given
		Member member = createMember();
		member.encodePassword(passwordEncoder);
		memberRepository.save(member);

		Login request = Login.builder()
			.uniqueMemberId("1")
			.password("saeeden1234!!")
			.build();

		// when
		JwtResponse result = authService.login(request);

		// then
		assertThat(result.getGrantType()).isEqualTo("Bearer");
		assertThat(result.getAccessToken()).isNotBlank();
		assertThat(result.getRefreshToken()).isNotBlank();
	}

	@Test
	@DisplayName("비밀번호가 다르면 로그인에 실패한다.")
	void loginFail() {
		// given
		Member member = createMember();
		member.encodePassword(passwordEncoder);
		memberRepository.save(member);

		Login request = Login.builder()
			.uniqueMemberId("1")
			.password("aaaaaa1234!!")
			.build();

		// when, then
		assertThatThrownBy(() -> authService.login(request))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("비밀번호가 일치하지 않습니다.");
	}

	private Member createMember() {
		return Member.create(
			"1",
			"김철수",
			"saeeden1234!!",
			LocalDate.of(2000, 11, 20),
			Division.DANIEL,
			Gender.MALE,
			LeaderRole.CAPTAIN,
			"010-1234-5678",
			"경기도 성남시"
		);
	}
}