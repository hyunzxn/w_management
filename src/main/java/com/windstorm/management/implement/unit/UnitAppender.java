package com.windstorm.management.implement.unit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.controller.admin.unit.request.UnitCreate;
import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.repository.unit.UnitRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnitAppender {
	private final UnitRepository unitRepository;

	@Transactional
	public Unit append(UnitCreate request) {
		Unit newUnit = Unit.create(request.division(), request.name());
		return unitRepository.save(newUnit);
	}
}
