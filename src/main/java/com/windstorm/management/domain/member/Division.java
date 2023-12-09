package com.windstorm.management.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Division {
	JOSEPH1("요셉1청"),
	JOSEPH2("요셉2청"),
	JOSEPH3("요셉3청"),
	DANIEL("다니엘청"),
	DAVID("다윗청"),
	LYDIA("루디아청");

	private final String desc;
}
