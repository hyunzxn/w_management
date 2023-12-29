package com.windstorm.management.api.user.unit.response;

import java.time.LocalDateTime;
import java.util.List;

import com.windstorm.management.api.user.cell.response.CellResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.unit.Unit;

import lombok.Builder;

@Builder
public record UnitResponse(
	Long id,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	Division division,
	String name,
	String managerName,
	List<CellResponse> cells
) {

	public static UnitResponse toResponse(Unit unit) {
		List<CellResponse> cells = unit.getCells().stream()
			.map(CellResponse::toResponse)
			.toList();

		return UnitResponse.builder()
			.id(unit.getId())
			.createdAt(unit.getCreatedAt())
			.modifiedAt(unit.getModifiedAt())
			.division(unit.getDivision())
			.name(unit.getName())
			.managerName(unit.getName().substring(0, 3))
			.cells(cells)
			.build();
	}
}
