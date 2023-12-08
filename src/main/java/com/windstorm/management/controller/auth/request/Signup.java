package com.windstorm.management.controller.auth.request;

import java.time.LocalDate;

import com.windstorm.management.domain.member.Division;
import com.windstorm.management.domain.member.Gender;
import com.windstorm.management.domain.member.LeaderRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Signup(
	@NotBlank(message = "교적번호를 입력해주세요.")
	String uniqueMemberId,

	@NotBlank(message = "이름을 입력해주세요.")
	String name,

	@NotNull(message = "생년월일을 입력해주세요.")
	LocalDate birthDate,

	@NotNull(message = "소속 청년부를 입력해주세요.")
	Division division,

	@NotNull(message = "성별을 입력해주세요.")
	Gender gender,

	@NotNull(message = "리더 역할을 입력해주세요.")
	LeaderRole role,

	@NotBlank(message = "휴대폰 번호를 입력해주세요.")
	String phoneNumber,

	@NotBlank(message = "주소를 입력해주세요.")
	String address
) {
}
