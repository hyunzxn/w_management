package com.windstorm.management.controller.admin.member.request;

import java.time.LocalDate;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;

import lombok.Builder;

@Builder
public record MemberModify(
	String uniqueId,
	String name,
	LocalDate birthDate,
	Division division,
	Gender gender,
	LeaderRole role,
	String phoneNumber,
	String address
) {
}
