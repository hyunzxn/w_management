package com.windstorm.management.service.member;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.api.admin.member.request.MemberModify;
import com.windstorm.management.api.user.member.request.PasswordModify;
import com.windstorm.management.api.user.member.response.MemberResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberModifier;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.repository.member.MemberRepository;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@AfterEach
	void cleanUp() {
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("PASTOR 권한 있는 유저가 일반 사용자의 정보를 수정할 수 있다.")
	void modifyAllInfoSuccess() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		String uniqueId = "1";
		MemberModify request = MemberModify.builder()
			.uniqueId("1")
			.name("김민수")
			.birthDate(LocalDate.of(2000, 11, 30))
			.division(Division.JOSEPH1)
			.gender(Gender.MALE)
			.role(LeaderRole.MANAGER)
			.phoneNumber("010-2222-3333")
			.address("서울특별시 강남구")
			.build();

		// when
		MemberResponse result = memberService.modify(uniqueId, request);

		// then
		assertThat(result)
			.extracting("name", "birthDate", "division", "role", "phoneNumber", "address")
			.containsExactlyInAnyOrder(
				"김민수", LocalDate.of(2000, 11, 30), Division.JOSEPH1, LeaderRole.MANAGER, "010-2222-3333", "서울특별시 강남구"
			);
	}

	@Test
	@DisplayName("클라이언트 측에서 사용자의 교적번호를 제대로 넘기지 않으면 사용자의 정보를 수정할 수 없다.")
	void modifyAllInfoFail1() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		String uniqueId = "";
		MemberModify request = MemberModify.builder()
			.uniqueId("1")
			.name("김민수")
			.birthDate(LocalDate.of(2000, 11, 30))
			.division(Division.JOSEPH1)
			.gender(Gender.MALE)
			.role(LeaderRole.MANAGER)
			.phoneNumber("010-2222-3333")
			.address("서울특별시 강남구")
			.build();

		// when, then
		assertThatThrownBy(() -> memberService.modify(uniqueId, request))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("교적번호가 입력되지 않았습니다.");
	}

	@Test
	@DisplayName("DB에 없는 사용자의 정보를 수정하려고 하면 실패한다.")
	void modifyAllInfoFail2() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		String uniqueId = "2";
		MemberModify request = MemberModify.builder()
			.uniqueId("2")
			.name("김민수")
			.birthDate(LocalDate.of(2000, 11, 30))
			.division(Division.JOSEPH1)
			.gender(Gender.MALE)
			.role(LeaderRole.MANAGER)
			.phoneNumber("010-2222-3333")
			.address("서울특별시 강남구")
			.build();

		// when, then
		assertThatThrownBy(() -> memberService.modify(uniqueId, request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("교적번호: " + uniqueId + "에 해당하는 데이터가 존재하지 않습니다.");
	}

	@Test
	@DisplayName("사용자의 이름으로 사용자를 조회할 수 있다.")
	void getByNameSuccess() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		String name = "김철수";

		// when
		List<MemberResponse> result = memberService.get(name);

		// then
		assertThat(result)
			.hasSize(1)
			.extracting("name", "division", "role")
			.containsExactlyInAnyOrder(
				tuple("김철수", Division.DANIEL, LeaderRole.CAPTAIN)
			);
	}

	@Test
	@DisplayName("클라이언트 측에서 사용자 이름을 제대로 보내주지 않으면 사용자를 조회할 수 없다.")
	void getByNameFail() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		String name = "";

		// when, then
		assertThatThrownBy(() -> memberService.get(name))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이름을 입력해주세요.");
	}

	@Test
	@DisplayName("사용자가 자신의 비밀번호를 수정할 수 있다.")
	@Transactional
	void modifyPasswordSuccess() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		String uniqueId = "1";

		PasswordModify request = PasswordModify.builder()
			.password("newPassword1234!!")
			.build();

		// when
		memberService.modifyPassword(uniqueId, request);

		// then
		assertThat(member.getPassword()).isNotEqualTo("saeeden1234!!");
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