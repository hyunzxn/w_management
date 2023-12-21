package com.windstorm.management.api.admin.unit.request;

import com.windstorm.management.domain.global.Division;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UnitCreate(

	@NotNull(message = "소속 청년부를 입력해주세요.")
	Division division,

	@NotBlank(message = "진 이름을 입력해주세요.")
	String name
) {
}
