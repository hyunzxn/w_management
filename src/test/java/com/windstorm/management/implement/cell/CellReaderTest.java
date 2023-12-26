package com.windstorm.management.implement.cell;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.repository.cell.CellRepository;

@ExtendWith(MockitoExtension.class)
class CellReaderTest {
	@Mock
	private CellRepository cellRepository;

	@InjectMocks
	private CellReader cellReader;

	@Test
	@DisplayName("셀을 셀 이름으로 조회할 수 있다.")
	void findCellSuccess() {
		// given
		String name = "홍길동셀";

		Cell cell = createCell();

		when(cellRepository.findByName(any(String.class))).thenReturn(Optional.of(cell));

		// when
		Cell findCell = cellReader.read(name);

		// then
		assertThat(findCell.getName()).isEqualTo("홍길동셀");
	}

	@Test
	@DisplayName("셀을 셀 이름으로 조회하는데 실패한다.")
	void findCellFail() {
		// given
		String name = "김철수셀";

		when(cellRepository.findByName(any(String.class))).thenThrow(new RuntimeException(name + "에 해당하는 셀이 존재하지 않습니다."));

		// when, then
		assertThatThrownBy(() -> cellReader.read(name))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(name + "에 해당하는 셀이 존재하지 않습니다.");
	}

	private Cell createCell() {
		return Cell.create("홍길동셀", Division.DANIEL);
	}

}