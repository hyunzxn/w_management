package com.windstorm.management.service.cell;

import org.springframework.stereotype.Service;

import com.windstorm.management.controller.cell.request.CellAddMember;
import com.windstorm.management.controller.cell.request.CellCreate;
import com.windstorm.management.controller.cell.response.CellResponse;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.implement.cell.CellAppender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CellService {
	private final CellAppender cellAppender;

	public CellResponse append(CellCreate request) {
		Cell newCell = cellAppender.append(request);
		return CellResponse.toResponse(newCell);
	}

	public void addMember(CellAddMember request) {
		cellAppender.addMember(request);
	}
}
