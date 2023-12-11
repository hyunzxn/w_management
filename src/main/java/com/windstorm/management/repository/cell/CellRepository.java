package com.windstorm.management.repository.cell;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windstorm.management.domain.cell.Cell;

public interface CellRepository extends JpaRepository<Cell, Long> {
	Optional<Cell> findByName(String name);
}
