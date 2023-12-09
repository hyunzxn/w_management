package com.windstorm.management.controller.member.response;

import java.time.LocalDate;

import com.windstorm.management.domain.member.Division;
import com.windstorm.management.domain.member.Gender;
import com.windstorm.management.domain.member.LeaderRole;
import com.windstorm.management.domain.member.Member;

import lombok.Builder;

@Builder
public record MemberResponse(
	Long id,
	String uniqueMemberId,
	String name,
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
			.uniqueMemberId(member.getUniqueMemberId())
			.name(member.getName())
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
