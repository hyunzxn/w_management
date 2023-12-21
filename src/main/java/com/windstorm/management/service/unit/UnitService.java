package com.windstorm.management.service.unit;

import org.springframework.stereotype.Service;

import com.windstorm.management.api.admin.unit.request.UnitCreate;
import com.windstorm.management.api.user.unit.response.UnitResponse;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.implement.unit.UnitAppender;
import com.windstorm.management.implement.unit.UnitReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitService {
	private final UnitAppender unitAppender;
	private final UnitReader unitReader;

	public UnitResponse append(UnitCreate request) {
		Unit newUnit = unitAppender.append(request);
		return UnitResponse.toResponse(newUnit);
	}

	public UnitResponse get(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("진 이름이 입력되지 않았습니다.");
		}
		Unit unit = unitReader.read(name);
		return UnitResponse.toResponse(unit);
	}
}
