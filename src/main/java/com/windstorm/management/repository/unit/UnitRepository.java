package com.windstorm.management.repository.unit;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windstorm.management.domain.unit.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {

	Optional<Unit> findByName(String name);
}
