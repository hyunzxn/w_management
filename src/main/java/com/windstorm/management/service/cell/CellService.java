package com.windstorm.management.service.cell;

import org.springframework.stereotype.Service;

import com.windstorm.management.api.admin.cell.request.CellAddMember;
import com.windstorm.management.api.admin.cell.request.CellCreate;
import com.windstorm.management.api.user.cell.response.CellResponse;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.implement.cell.CellAppender;
import com.windstorm.management.implement.cell.CellManager;
import com.windstorm.management.implement.cell.CellReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CellService {
	private final CellAppender cellAppender;
	private final CellReader cellReader;
	private final CellManager cellManager;

	/**
	 * for Admin
	 */
	public CellResponse append(CellCreate request) {
		Cell newCell = cellAppender.append(request);
		return CellResponse.toResponse(newCell);
	}

	/**
	 * for Admin
	 */
	public void addMember(CellAddMember request) {
		cellManager.addMember(request);
	}

	public CellResponse get(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("셀 이름이 입력되지 않았습니다.");
		}

		Cell cell = cellReader.read(name);
		return CellResponse.toResponse(cell);
	}
}
