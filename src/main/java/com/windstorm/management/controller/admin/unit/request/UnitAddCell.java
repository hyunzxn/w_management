package com.windstorm.management.controller.admin.unit.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UnitAddCell(
	@NotBlank(message = "진 이름을 입력해주세요.")
	String unitName,

	@NotBlank(message = "셀 이름을 입력해주세요.")
	String cellName
) {
}
