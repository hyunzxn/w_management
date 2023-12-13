package com.windstorm.management.repository.unit;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windstorm.management.domain.unit.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
