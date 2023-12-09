package com.windstorm.management.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
	MALE("남성"),
	FEMALE("여성");

	private final String desc;
}
