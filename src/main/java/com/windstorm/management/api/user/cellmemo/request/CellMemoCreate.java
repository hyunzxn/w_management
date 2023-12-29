package com.windstorm.management.api.user.cellmemo.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CellMemoCreate(
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식을 다시 한 번 확인해주세요.")
	LocalDate date,
	String text,
	String cellName
) {
}
