package com.windstorm.management.implement.cell;

import java.util.List;

import org.springframework.stereotype.Component;

import com.windstorm.management.controller.cell.request.CellAddMember;
import com.windstorm.management.controller.cell.request.CellCreate;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.repository.cell.CellRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CellAppender {
	private final CellRepository cellRepository;
	private final MemberReader memberReader;
	private final CellReader cellReader;

	@Transactional
	public Cell append(CellCreate request) {
		Cell newCell = Cell.create(request.name(), request.division());
		return cellRepository.save(newCell);
	}

	@Transactional
	public void addMember(CellAddMember request) {
		Member member = memberReader.read(request.uniqueId());
		Cell cell = cellReader.read(request.cellName());

		cell.addMember(member);
	}
}
