package com.windstorm.management.service.unit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.admin.unit.request.UnitAddCell;
import com.windstorm.management.controller.admin.unit.request.UnitCreate;
import com.windstorm.management.controller.unit.response.UnitResponse;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.implement.cell.CellReader;
import com.windstorm.management.implement.unit.UnitAppender;
import com.windstorm.management.implement.unit.UnitReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitService {
	private final UnitAppender unitAppender;
	private final UnitReader unitReader;
	private final CellReader cellReader;

	public UnitResponse append(UnitCreate request) {
		Unit newUnit = unitAppender.append(request);
		return UnitResponse.toResponse(newUnit);
	}

	@Transactional
	public void addCell(UnitAddCell request) {
		Unit unit = unitReader.read(request.unitName());
		Cell cell = cellReader.read(request.cellName());

		unit.addCell(cell);
	}
}
