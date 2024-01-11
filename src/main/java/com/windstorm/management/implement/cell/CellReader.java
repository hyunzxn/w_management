package com.windstorm.management.implement.cell;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.repository.cell.CellRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CellReader {
	private final CellRepository cellRepository;

	public Cell read(String name) {
		return cellRepository.findByName(name)
			.orElseThrow(() -> new RuntimeException(name + "에 해당하는 셀이 존재하지 않습니다."));
	}

	public Cell readWithDivision(String name, Division division) {
		return cellRepository.findByNameAndDivision(name, division)
			.orElseThrow(() -> new RuntimeException(name + "에 해당하는 셀이 존재하지 않습니다."));
	}
}
