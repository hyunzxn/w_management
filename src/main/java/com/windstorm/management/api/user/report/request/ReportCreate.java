package com.windstorm.management.api.user.report.request;

import java.time.LocalDate;

import com.windstorm.management.domain.global.Division;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReportCreate(
	@NotNull(message = "심방 날짜를 입력해주세요.")
	LocalDate date,

	@NotBlank(message = "심방 대상자의 이름을 입력해주세요.")
	String targetName,

	@NotBlank(message = "심방 내용을 입력해주세요.")
	String content,

	@NotNull(message = "소속 청년부를 입력해주세요.")
	Division division,

	String specialNote,

	String targetPrayRequest
) {
}
