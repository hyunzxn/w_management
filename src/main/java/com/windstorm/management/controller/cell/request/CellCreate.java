package com.windstorm.management.controller.cell.request;

import com.windstorm.management.domain.global.Division;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CellCreate(
	@NotBlank(message = "셀 이름을 입력해주세요.")
	String name,

	@NotNull(message = "청년부 이름을 입력해주세요.")
	Division division
) {
}
