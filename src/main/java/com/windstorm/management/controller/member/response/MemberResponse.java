package com.windstorm.management.controller.member.response;

import java.time.LocalDate;

import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;

import lombok.Builder;

@Builder
public record MemberResponse(
	Long id,
	String uniqueMemberId,
	String name,
	Cell cell,
	LocalDate birthDate,
	int ageGroup,
	Division division,
	Gender gender,
	LeaderRole role,
	String phoneNumber,
	String address
) {

	public static MemberResponse toResponse(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.uniqueMemberId(member.getUniqueId())
			.name(member.getName())
			.cell(member.getCell())
			.birthDate(member.getBirthDate())
			.ageGroup(member.calculateAgeGroup(member.getBirthDate()))
			.division(member.getDivision())
			.gender(member.getGender())
			.role(member.getRole())
			.phoneNumber(member.getPhoneNumber())
			.address(member.getAddress())
			.build();
	}
}
