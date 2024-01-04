package com.windstorm.management.api.admin.member.request;

import java.time.LocalDate;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberModify(
	@NotBlank(message = "수정하려는 청년의 교적번호를 입력해주세요.")
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
