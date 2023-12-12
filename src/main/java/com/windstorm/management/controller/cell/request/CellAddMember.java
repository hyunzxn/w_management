package com.windstorm.management.controller.cell.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CellAddMember(
	@NotBlank(message = "셀 이름을 입력해주세요.")
	String cellName,

	@NotBlank(message = "셀원 교적번호를 입력해주세요.")
	String uniqueId
) {
}
