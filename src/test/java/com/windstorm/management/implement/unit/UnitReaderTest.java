package com.windstorm.management.implement.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.repository.unit.UnitRepository;

@ExtendWith(MockitoExtension.class)
class UnitReaderTest {
	@Mock
	private UnitRepository unitRepository;

	@InjectMocks
	private UnitReader unitReader;

	@Test
	@DisplayName("진 이름으로 진을 조회할 수 있다.")
	void readUnitSuccess() {
		// given
		String name = "홍길동진";
		Unit unit = Unit.create(Division.DANIEL, "홍길동진");

		when(unitRepository.findByName(any(String.class))).thenReturn(Optional.of(unit));

		// when
		Unit result = unitReader.read(name);

		// then
		assertThat(result.getDivision()).isEqualTo(Division.DANIEL);
		assertThat(result.getName()).isEqualTo("홍길동진");
	}

	@Test
	@DisplayName("진 조회에 실패한다.")
	void readUnitFail() {
		// given
		String name = "홍길동진";

		when(unitRepository.findByName(any(String.class))).thenThrow(new RuntimeException(name + "에 해당하는 진이 없습니다."));

		// when, then
		assertThatThrownBy(() -> unitReader.read(name))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(name + "에 해당하는 진이 없습니다.");
	}
}