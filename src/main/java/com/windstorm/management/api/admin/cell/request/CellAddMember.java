package com.windstorm.management.api.admin.cell.request;

import com.windstorm.management.domain.global.Division;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CellAddMember(
	@NotBlank(message = "셀 이름을 입력해주세요.")
	String cellName,

	@NotNull(message = "청년부를 입력해주세요.")
	Division division,

	@NotBlank(message = "셀원 교적번호를 입력해주세요.")
	String uniqueId
) {
}
