package com.windstorm.management.api.user.member.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;

import lombok.Builder;

@Builder
public record MemberResponse(
	Long id,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	String uniqueMemberId,
	String name,
	String unitName,
	String cellName,
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
			.createdAt(member.getCreatedAt())
			.modifiedAt(member.getModifiedAt())
			.uniqueMemberId(member.getUniqueId())
			.name(member.getName())
			.unitName(member.getCell() == null ? "새가족" : member.getCell().getUnit().getName())
			.cellName(member.getCell() == null ? "새가족" : member.getCell().getName())
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
