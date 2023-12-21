package com.windstorm.management.api.user.cell.response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.windstorm.management.api.user.member.response.MemberResponse;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.LeaderRole;

import lombok.Builder;

@Builder
public record CellResponse(
	String name,
	List<MemberResponse> members
) {

	public static CellResponse toResponse(Cell cell) {
		List<MemberResponse> members = cell.getMembers().stream()
			.map(MemberResponse::toResponse)
			.collect(Collectors.toList());

		setCaptainFirstInList(members);

		return CellResponse.builder()
			.name(cell.getName())
			.members(members)
			.build();
	}

	private static void setCaptainFirstInList(List<MemberResponse> members) {
		Optional<MemberResponse> captain = members.stream()
			.filter(memberResponse -> memberResponse.role() == LeaderRole.CAPTAIN)
			.findFirst();

		captain.ifPresent(memberResponse -> {
			members.remove(memberResponse);
			members.add(0, memberResponse);
		});
	}
}
