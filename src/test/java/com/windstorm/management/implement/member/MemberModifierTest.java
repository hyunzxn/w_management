package com.windstorm.management.implement.member;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.windstorm.management.api.admin.member.request.MemberCellModify;
import com.windstorm.management.api.admin.member.request.MemberModify;
import com.windstorm.management.api.user.member.request.PasswordModify;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;

@ExtendWith(MockitoExtension.class)
class MemberModifierTest {
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private MemberModifier memberModifier;

	@Test
	@DisplayName("유저가 자신의 비밀번호를 수정할 수 있다.")
	void modifyPassword() {
		// given
		Member member = createMember();

		PasswordModify request = PasswordModify.builder()
			.password("newPassword1234!!")
			.build();

		// when
		memberModifier.modifyPassword(member, request);

		// then
		verify(passwordEncoder, times(1)).encode(any(String.class));
	}

	@Test
	@DisplayName("PASTOR 권한 있는 유저가 다른 유저의 전체 정보를 수정할 수 있다.")
	void modifyAllInfo() {
		// given
		Member member = createMember();

		MemberModify request = MemberModify.builder()
			.uniqueId("1")
			.name("김민수")
			.birthDate(LocalDate.of(2000, 11, 20))
			.division(Division.LYDIA)
			.gender(Gender.MALE)
			.role(LeaderRole.MANAGER)
			.phoneNumber("010-2222-3333")
			.address("경기도 용인시")
			.build();

		// when
		memberModifier.modify(member, request);

		// then
		assertThat(member.getName()).isEqualTo("김민수");
		assertThat(member.getDivision()).isEqualTo(Division.LYDIA);
		assertThat(member.getRole()).isEqualTo(LeaderRole.MANAGER);
		assertThat(member.getPhoneNumber()).isEqualTo("010-2222-3333");
		assertThat(member.getAddress()).isEqualTo("경기도 용인시");
	}

	@Test
	@DisplayName("청년이 속한 셀을 변경할 수 있다.")
	void modifyCell() {
		// given
		Member member = createMember();
		member.defineCell(Cell.create("민수셀", Division.DANIEL));

		Cell newCell = createCell();

		// when
		memberModifier.modifyCell(member, newCell);

		// then
		assertThat(member.getCell().getName()).isEqualTo("짱구셀");
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

	private Cell createCell() {
		return Cell.create(
			"짱구셀",
			Division.DANIEL
		);
	}
}