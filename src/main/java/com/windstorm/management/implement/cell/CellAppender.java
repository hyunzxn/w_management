package com.windstorm.management.implement.cell;

import org.springframework.stereotype.Component;

import com.windstorm.management.api.admin.cell.request.CellAddMember;
import com.windstorm.management.api.admin.cell.request.CellCreate;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.implement.unit.UnitReader;
import com.windstorm.management.repository.cell.CellRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CellAppender {
	private final CellRepository cellRepository;
	private final UnitReader unitReader;

	@Transactional
	public Cell append(CellCreate request) {
		Unit unit = unitReader.read(request.unitName());
		Cell newCell = Cell.create(request.name(), request.division());
		newCell.defineUnit(unit);
		return cellRepository.save(newCell);
	}
}
