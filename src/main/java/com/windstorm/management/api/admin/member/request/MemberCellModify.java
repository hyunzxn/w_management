package com.windstorm.management.api.admin.member.request;

import lombok.Builder;

@Builder
public record MemberCellModify(
	String uniqueId,
	String cellName
) {
}
