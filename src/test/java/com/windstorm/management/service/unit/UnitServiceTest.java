package com.windstorm.management.service.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.api.admin.unit.request.UnitCreate;
import com.windstorm.management.api.user.unit.response.UnitResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.repository.unit.UnitRepository;

@SpringBootTest
@ActiveProfiles("test")
class UnitServiceTest {
	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private UnitService unitService;

	@AfterEach
	void cleanUp() {
		unitRepository.deleteAll();
	}

	@Test
	@DisplayName("새로운 진을 추가할 수 있다.")
	void unitAppendSuccess() {
		// given
		UnitCreate request = UnitCreate.builder()
			.division(Division.DANIEL)
			.name("홍길동진")
			.build();

		// when
		UnitResponse result = unitService.append(request);

		// then
		assertThat(result.division()).isEqualTo(Division.DANIEL);
		assertThat(result.name()).isEqualTo("홍길동진");
	}

	@Test
	@DisplayName("이름으로 진 조회에 성공한다.")
	@Transactional
	void readUnitSuccess() {
		// given
		Unit unit = createUnit();
		unitRepository.save(unit);

		String name = "홍길동진";

		// when
		UnitResponse result = unitService.get(name);

		// then
		assertThat(result.division()).isEqualTo(Division.DANIEL);
		assertThat(result.name()).isEqualTo("홍길동진");
	}

	@Test
	@DisplayName("진 조회에 실패한다.")
	@Transactional
	void readUnitFail() {
		// given
		Unit unit = createUnit();
		unitRepository.save(unit);

		String name = "";

		// when, then
		assertThatThrownBy(() -> unitService.get(name))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("진 이름이 입력되지 않았습니다.");
	}

	private Unit createUnit() {
		return Unit.builder()
			.division(Division.DANIEL)
			.name("홍길동진")
			.build();
	}
}