package com.windstorm.management.controller.cell.response;

import java.util.List;

import com.windstorm.management.controller.member.response.MemberResponse;
import com.windstorm.management.domain.cell.Cell;

import lombok.Builder;

@Builder
public record CellResponse(
	String name,
	List<MemberResponse> members
) {

	public static CellResponse toResponse(Cell cell) {
		List<MemberResponse> members = cell.getMembers().stream()
			.map(MemberResponse::toResponse)
			.toList();

		return CellResponse.builder()
			.name(cell.getName())
			.members(members)
			.build();
	}
}
