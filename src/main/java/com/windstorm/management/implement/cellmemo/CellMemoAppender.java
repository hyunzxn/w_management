package com.windstorm.management.implement.cellmemo;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.api.user.cellmemo.request.CellMemoCreate;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.cellmemo.CellMemo;
import com.windstorm.management.implement.cell.CellReader;
import com.windstorm.management.repository.cellmemo.CellMemoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CellMemoAppender {
	private final CellMemoRepository cellMemoRepository;
	private final CellReader cellReader;

	@Transactional
	public void append(CellMemoCreate request) {
		Cell cell = cellReader.read(request.cellName());
		CellMemo memo = CellMemo.create(request.date(), request.text(), cell);
		cellMemoRepository.save(memo);
	}
}
