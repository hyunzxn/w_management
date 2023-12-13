package com.windstorm.management.service.unit;

import org.springframework.stereotype.Service;

import com.windstorm.management.controller.admin.unit.request.UnitCreate;
import com.windstorm.management.controller.unit.response.UnitResponse;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.implement.unit.UnitAppender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitService {
	private final UnitAppender unitAppender;

	public UnitResponse append(UnitCreate request) {
		Unit newUnit = unitAppender.append(request);
		return UnitResponse.toResponse(newUnit);
	}
}
