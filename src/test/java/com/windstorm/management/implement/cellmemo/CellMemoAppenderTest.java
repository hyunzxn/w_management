package com.windstorm.management.implement.cellmemo;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.api.user.cellmemo.request.CellMemoCreate;
import com.windstorm.management.domain.cellmemo.CellMemo;
import com.windstorm.management.implement.cell.CellReader;
import com.windstorm.management.repository.cellmemo.CellMemoRepository;

@ExtendWith(MockitoExtension.class)
class CellMemoAppenderTest {
	@Mock
	private CellMemoRepository cellMemoRepository;

	@Mock
	private CellReader cellReader;

	@InjectMocks
	private CellMemoAppender cellMemoAppender;

	@Test
	@DisplayName("셀 특이사항을 입력할 수 있다.")
	void append() {
		// given
		CellMemoCreate request = CellMemoCreate.builder()
			.date(LocalDate.of(2023, 12, 29))
			.text("셀 특이사항 입니다.")
			.cellName("홍길동셀")
			.build();

		// when
		cellMemoAppender.append(request);

		// then
		verify(cellReader, times(1)).read(any(String.class));
		verify(cellMemoRepository, times(1)).save(any(CellMemo.class));
	}
}