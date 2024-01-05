package com.windstorm.management.api.admin.cell.request;

import lombok.Builder;

@Builder
public record UnitModify(
	String cellName,
	String unitName
) {
}
