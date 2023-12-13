package com.windstorm.management.controller.unit.response;

import java.util.List;

import com.windstorm.management.controller.cell.response.CellResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.unit.Unit;

import lombok.Builder;

@Builder
public record UnitResponse(
	Division division,
	String name,
	List<CellResponse> cells
) {

	public static UnitResponse toResponse(Unit unit) {
		List<CellResponse> cells = unit.getCells().stream()
			.map(CellResponse::toResponse)
			.toList();

		return UnitResponse.builder()
			.division(unit.getDivision())
			.name(unit.getName())
			.cells(cells)
			.build();
	}
}