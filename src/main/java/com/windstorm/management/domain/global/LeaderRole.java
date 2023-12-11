package com.windstorm.management.domain.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LeaderRole {
	NONE("셀원"),
	CAPTAIN("셀장"),
	MANAGER("간사"),
	HEAD_MANAGER("간사장"),
	PASTOR("사역자");

	private final String desc;
}
