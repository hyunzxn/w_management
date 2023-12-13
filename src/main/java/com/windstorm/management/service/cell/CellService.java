package com.windstorm.management.service.cell;

import org.springframework.stereotype.Service;

import com.windstorm.management.controller.admin.cell.request.CellAddMember;
import com.windstorm.management.controller.admin.cell.request.CellCreate;
import com.windstorm.management.controller.cell.response.CellResponse;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.implement.cell.CellAppender;
import com.windstorm.management.implement.cell.CellReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CellService {
	private final CellAppender cellAppender;
	private final CellReader cellReader;

	public CellResponse append(CellCreate request) {
		Cell newCell = cellAppender.append(request);
		return CellResponse.toResponse(newCell);
	}

	public void addMember(CellAddMember request) {
		cellAppender.addMember(request);
	}

	public CellResponse get(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("셀 이름이 입력되지 않았습니다.");
		}

		Cell cell = cellReader.read(name);
		return CellResponse.toResponse(cell);
	}
}
