package com.windstorm.management.implement.cell;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.api.admin.cell.request.CellAddMember;
import com.windstorm.management.api.admin.cell.request.CellCreate;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.implement.unit.UnitReader;
import com.windstorm.management.repository.cell.CellRepository;

@ExtendWith(MockitoExtension.class)
class CellAppenderTest {
	@Mock
	private CellRepository cellRepository;

	@Mock
	private MemberReader memberReader;

	@Mock
	private CellReader cellReader;

	@Mock
	private UnitReader unitReader;

	@InjectMocks
	private CellAppender cellAppender;

	@Test
	@DisplayName("새로운 셀을 생성할 수 있다.")
	void appendNewCellSuccess() {
		// given
		CellCreate request = CellCreate.builder()
			.name("홍길동셀")
			.division(Division.DANIEL)
			.unitName("김철수진")
			.build();

		Unit unit = Unit.create(Division.DANIEL, "김철수진");
		Cell cell = Cell.create("홍길동셀", Division.DANIEL);
		cell.defineUnit(unit);

		when(unitReader.read(any(String.class))).thenReturn(unit);
		when(cellRepository.save(any(Cell.class))).thenReturn(cell);

		// when
		Cell result = cellAppender.append(request);

		// then
		assertThat(result.getDivision()).isEqualTo(Division.DANIEL);
		assertThat(result.getName()).isEqualTo("홍길동셀");
		assertThat(result.getUnit().getName()).isEqualTo("김철수진");
	}

	@Test
	@DisplayName("셀이 소속될 진이 없으면 셀을 생성할 수 없다.")
	void appendNewCellFail() {
		// given
		String unitName = "김철수진";
		CellCreate request = CellCreate.builder()
			.name("홍길동셀")
			.division(Division.DANIEL)
			.unitName("김철수진")
			.build();

		when(unitReader.read(any(String.class))).thenThrow(new RuntimeException(unitName + "에 해당하는 진이 없습니다."));

		// when, then
		assertThatThrownBy(() -> cellAppender.append(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(unitName + "에 해당하는 진이 없습니다.");
	}

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
		cellAppender.addMember(request);

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
		assertThatThrownBy(() -> cellAppender.addMember(request))
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
		assertThatThrownBy(() -> cellAppender.addMember(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(cellName + "에 해당하는 셀이 존재하지 않습니다.");
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