package com.windstorm.management.api.admin.member.request;

import com.windstorm.management.domain.global.Division;

import lombok.Builder;

@Builder
public record MemberCellModify(
	String uniqueId,
	String cellName,
	Division division
) {
}
