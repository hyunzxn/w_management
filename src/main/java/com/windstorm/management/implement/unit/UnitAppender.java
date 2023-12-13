package com.windstorm.management.implement.unit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.admin.unit.request.UnitAddCell;
import com.windstorm.management.controller.admin.unit.request.UnitCreate;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.implement.cell.CellReader;
import com.windstorm.management.repository.unit.UnitRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnitAppender {
	private final UnitRepository unitRepository;
	private final UnitReader unitReader;
	private final CellReader cellReader;

	@Transactional
	public Unit append(UnitCreate request) {
		Unit newUnit = Unit.create(request.division(), request.name());
		return unitRepository.save(newUnit);
	}

	@Transactional
	public void addCell(UnitAddCell request) {
		Cell cell = cellReader.read(request.cellName());
		Unit unit = unitReader.read(request.unitName());

		unit.addCell(cell);
	}
}
