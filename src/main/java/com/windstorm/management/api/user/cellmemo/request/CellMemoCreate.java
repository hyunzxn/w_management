package com.windstorm.management.api.user.cellmemo.request;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record CellMemoCreate(
	LocalDate date,
	String text,
	String cellName
) {
}
