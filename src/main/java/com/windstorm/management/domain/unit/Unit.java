package com.windstorm.management.domain.unit;

import java.util.ArrayList;
import java.util.List;

import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Division division;

	private String name;

	@OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
	private List<Cell> cells = new ArrayList<>();

	@Builder
	private Unit(Division division, String name) {
		this.division = division;
		this.name = name;
	}

	public static Unit create(Division division, String name) {
		return Unit.builder()
			.division(division)
			.name(name)
			.build();
	}
}
