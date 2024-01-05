package com.windstorm.management.service.cell;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.api.admin.cell.request.CellAddMember;
import com.windstorm.management.api.admin.cell.request.CellCreate;
import com.windstorm.management.api.admin.cell.request.UnitModify;
import com.windstorm.management.api.user.cell.response.CellResponse;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.repository.cell.CellRepository;
import com.windstorm.management.repository.member.MemberRepository;
import com.windstorm.management.repository.unit.UnitRepository;

@SpringBootTest
@ActiveProfiles("test")
class CellServiceTest {
	@Autowired
	private CellRepository cellRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CellService cellService;

	@AfterEach
	void cleanUp() {
		cellRepository.deleteAll();
		unitRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("새로운 셀을 추가할 수 있다.")
	void appendNewCellSuccess() {
		// given
		Unit unit = createUnit();
		unitRepository.save(unit);

		CellCreate request = CellCreate.builder()
			.division(Division.DANIEL)
			.name("홍길동셀")
			.unitName("김철수진")
			.build();

		// when
		CellResponse result = cellService.append(request);

		// then
		assertThat(result.name()).isEqualTo("홍길동셀");
	}

	@Test
	@DisplayName("존재하지 않는 진에 셀을 추가할 수 없다.")
	void appendNewCellFail() {
		// given
		CellCreate request = CellCreate.builder()
			.division(Division.DANIEL)
			.name("홍길동셀")
			.unitName("김철수진")
			.build();

		// when, then
		assertThatThrownBy(() -> cellService.append(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(request.unitName() + "에 해당하는 진이 없습니다.");
	}

	@Test
	@DisplayName("셀에 새로운 셀원을 추가할 수 있다.")
	@Transactional
	void addMemberSuccess() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		Cell cell = createCell();
		cellRepository.save(cell);

		CellAddMember request = CellAddMember.builder()
			.cellName("홍길동셀")
			.uniqueId("1")
			.build();

		// when
		cellService.addMember(request);

		// then
		assertThat(cell.getMembers())
			.hasSize(1)
			.extracting("uniqueId", "name")
			.containsExactlyInAnyOrder(
				tuple("1", "김철수")
			);
	}

	@Test
	@DisplayName("존재하지 않는 유저를 셀에 추가할 수 없다.")
	void addMemberFail1() {
		// given
		Cell cell = createCell();
		cellRepository.save(cell);

		CellAddMember request = CellAddMember.builder()
			.cellName("홍길동셀")
			.uniqueId("1")
			.build();

		// when, then
		assertThatThrownBy(() -> cellService.addMember(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("교적번호: " + request.uniqueId() + "에 해당하는 데이터가 존재하지 않습니다.");
	}

	@Test
	@DisplayName("존재하지 않는 셀에 유저를 추가할 수 없다.")
	void addMemberFail2() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		CellAddMember request = CellAddMember.builder()
			.cellName("홍길동셀")
			.uniqueId("1")
			.build();

		// when, then
		assertThatThrownBy(() -> cellService.addMember(request))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(request.cellName() + "에 해당하는 셀이 존재하지 않습니다.");
	}

	@Test
	@DisplayName("셀 이름으로 조회할 수 있다.")
	@Transactional
	void getCell() {
		// given
		String name = "홍길동셀";

		Cell cell = createCell();
		cellRepository.save(cell);

		// when
		CellResponse result = cellService.get(name);

		// then
		assertThat(result.name()).isEqualTo("홍길동셀");
	}

	@Test
	@DisplayName("존재하지 않는 셀은 조회할 수 없다.")
	@Transactional
	void getCellFail1() {
		// given
		String name = "홍길동셀";

		// when, then
		assertThatThrownBy(() -> cellService.get(name))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(name + "에 해당하는 셀이 존재하지 않습니다.");
	}

	@Test
	@DisplayName("셀 이름을 입력하지 않으면 셀을 조회할 수 없다.")
	@Transactional
	void getCellFail2() {
		// given
		String name = "";

		Cell cell = createCell();
		cellRepository.save(cell);

		// when, then
		assertThatThrownBy(() -> cellService.get(name))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("셀 이름이 입력되지 않았습니다.");
	}

	@Test
	@DisplayName("셀이 소속된 진을 변경할 수 있다.")
	@Transactional
	void modifyUnit() {
		// given
		List<Unit> units = List.of(
			Unit.create(Division.DANIEL, "박짱구진"),
			Unit.create(Division.DANIEL, "김철수진")
		);
		unitRepository.saveAll(units);

		Cell cell = createCell();
		cell.defineUnit(units.get(0));
		cellRepository.save(cell);

		UnitModify request = UnitModify.builder()
			.cellName("홍길동셀")
			.unitName("김철수진")
			.build();

		// when
		cellService.modifyUnit(request);

		// then
		assertThat(cell.getUnit().getName()).isEqualTo("김철수진");
	}

	private Unit createUnit() {
		return Unit.create(Division.DANIEL, "김철수진");
	}

	private Cell createCell() {
		return Cell.create("홍길동셀", Division.DANIEL);
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