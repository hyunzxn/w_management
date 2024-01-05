package com.windstorm.management.implement.cell;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.api.admin.cell.request.CellAddMember;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.implement.member.MemberReader;

@ExtendWith(MockitoExtension.class)
class CellManagerTest {
	@Mock
	private MemberReader memberReader;

	@Mock
	private CellReader cellReader;

	@InjectMocks
	private CellManager cellManager;

	@Test
	@DisplayName("셀에 셀원을 추가할 수 있다.")
	void addMemberSuccess() {
		// given
		CellAddMember request = CellAddMember.builder()
			.uniqueId("1")
			.cellName("홍길동셀")
			.build();

		Member member = createMember();
		Cell cell = createCell();

		when(memberReader.read(any(String.class))).thenReturn(member);
		when(cellReader.read(any(String.class))).thenReturn(cell);

		// when
		cellManager.addMember(request);

		// then
		assertThat(cell.getMembers())
			.hasSize(1)
			.extracting("uniqueId", "name")
			.containsExactlyInAnyOrder(
				tuple("1", "김철수")
			);
	}

	@Test
	@DisplayName("존재하지 않는 유저는 셀에 추가할 수 없다.")
	void addMemberFail1() {
		// given
		String uniqueId = "100";

		CellAddMember request = CellAddMember.builder()
			.uniqueId("1")
			.cellName("홍길동셀")
			.build();

		when(memberReader.read(any(String.class))).thenThrow(new RuntimeException("교적번호: " + uniqueId + "에 해당하는 데이터가 존재하지 않습니다."));

		// when, then
		assertThatThrownBy(() -> cellManager.addMember(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("교적번호: " + uniqueId + "에 해당하는 데이터가 존재하지 않습니다.");
	}

	@Test
	@DisplayName("존재하지 않는 셀에 유저를 추가할 수 없다.")
	void addMemberFail2() {
		// given
		String cellName = "김짱구셀";

		CellAddMember request = CellAddMember.builder()
			.uniqueId("1")
			.cellName("홍길동셀")
			.build();

		when(cellReader.read(any(String.class))).thenThrow(new RuntimeException(cellName + "에 해당하는 셀이 존재하지 않습니다."));

		// when, then
		assertThatThrownBy(() -> cellManager.addMember(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(cellName + "에 해당하는 셀이 존재하지 않습니다.");
	}

	@Test
	@DisplayName("셀이 소속된 진을 변경할 수 있다.")
	void modifyUnit() {
		// given
		Cell cell = createCell();
		cell.defineUnit(Unit.create(Division.DANIEL, "김짱구진"));

		Unit newUnit = Unit.create(Division.DANIEL, "박철수진");

		// when
		cellManager.modify(cell, newUnit);

		// then
		assertThat(cell.getUnit().getName()).isEqualTo("박철수진");
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
		return Cell.create("홍길동셀", Division.DANIEL);
	}
}