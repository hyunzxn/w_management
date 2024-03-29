package com.windstorm.management.implement.cell;

import org.springframework.stereotype.Component;

import com.windstorm.management.api.admin.cell.request.CellAddMember;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.implement.member.MemberReader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CellManager {
	private final MemberReader memberReader;
	private final CellReader cellReader;

	@Transactional
	public void addMember(CellAddMember request) {
		Member member = memberReader.read(request.uniqueId());
		Cell cell = cellReader.readWithDivision(request.cellName(), request.division());

		cell.addMember(member);
	}

	@Transactional
	public void modify(Cell cell, Unit newUnit) {
		cell.defineUnit(newUnit);
	}
}
