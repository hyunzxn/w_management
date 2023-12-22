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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.windstorm.management.api.user.auth.request.Signup;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.member.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberAppenderTest {
	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private MemberAppender memberAppender;

	@Test
	@DisplayName("새로운 유저가 회원가입을 할 수 있다.")
	void signUpSuccess() {
		// given
		Signup request = Signup.builder()
			.uniqueMemberId("12345")
			.name("홍길동")
			.birthDate(LocalDate.of(2000, 9, 15))
			.division(Division.DANIEL)
			.gender(Gender.MALE)
			.role(LeaderRole.MANAGER)
			.phoneNumber("010-1234-5678")
			.address("서울특별시 강남구")
			.build();
		Member newMember = createMember(request);
		newMember.encodePassword(passwordEncoder);

		when(memberRepository.save(any(Member.class))).thenReturn(newMember);

		// when
		Member result = memberAppender.append(request);

		// then
		assertThat(result.getUniqueId()).isEqualTo("12345");
	}

	@Test
	@DisplayName("이미 등록된 동일한 교적번호의 유저를 추가할 수 없다.")
	void signUpFail() {
		// given
		Signup request = Signup.builder()
			.uniqueMemberId("12345")
			.name("홍길동")
			.birthDate(LocalDate.of(2000, 9, 15))
			.division(Division.DANIEL)
			.gender(Gender.MALE)
			.role(LeaderRole.MANAGER)
			.phoneNumber("010-1234-5678")
			.address("서울특별시 강남구")
			.build();
		Member newMember = createMember(request);
		newMember.encodePassword(passwordEncoder);

		when(memberRepository.save(any(Member.class))).thenThrow(
			new RuntimeException("교적번호" + request.uniqueMemberId() + "에 해당하는 데이터가 이미 존재합니다.")
		);

		// when
		assertThatThrownBy(() -> memberAppender.append(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("교적번호" + request.uniqueMemberId() + "에 해당하는 데이터가 이미 존재합니다.");
	}

	private Member createMember(Signup request) {
		return Member.create(
			request.uniqueMemberId(),
			request.name(),
			"saeeden1234!!",
			request.birthDate(),
			request.division(),
			request.gender(),
			request.role(),
			request.phoneNumber(),
			request.address()
		);
	}
}