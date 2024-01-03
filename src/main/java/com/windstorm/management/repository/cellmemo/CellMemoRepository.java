package com.windstorm.management.repository.cellmemo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windstorm.management.domain.cellmemo.CellMemo;

public interface CellMemoRepository extends JpaRepository<CellMemo, Long> {
}
