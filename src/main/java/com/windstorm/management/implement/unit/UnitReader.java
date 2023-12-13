package com.windstorm.management.implement.unit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.unit.Unit;
import com.windstorm.management.repository.unit.UnitRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnitReader {
	private final UnitRepository unitRepository;

	public Unit read(String name) {
		return unitRepository.findByName(name).orElseThrow(() -> new RuntimeException(name + "에 해당하는 진이 없습니다."));
	}
}
