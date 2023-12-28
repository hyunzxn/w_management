package com.windstorm.management.implement.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.api.admin.unit.request.UnitCreate;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.repository.unit.UnitRepository;

@ExtendWith(MockitoExtension.class)
class UnitAppenderTest {
	@Mock
	private UnitRepository unitRepository;

	@InjectMocks
	private UnitAppender unitAppender;

	@Test
	@DisplayName("새로운 진을 추가할 수 있다.")
	void appendNewUnitSuccess() {
		// given
		UnitCreate request = UnitCreate.builder()
			.division(Division.DANIEL)
			.name("홍길동진")
			.build();

		Unit unit = Unit.create(Division.DANIEL, "홍길동진");

		when(unitRepository.save(any(Unit.class))).thenReturn(unit);

		// when
		Unit result = unitAppender.append(request);

		// then
		assertThat(result.getName()).isEqualTo("홍길동진");
		assertThat(result.getDivision()).isEqualTo(Division.DANIEL);
	}
}