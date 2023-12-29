package com.windstorm.management.domain.cellmemo;

import java.time.LocalDate;

import com.windstorm.management.domain.BaseTimeEntity;
import com.windstorm.management.domain.cell.Cell;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CellMemo extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	@Lob
	private String text;

	@ManyToOne
	@JoinColumn(name = "cell_id")
	private Cell cell;

	@Builder
	private CellMemo(LocalDate date, String text, Cell cell) {
		this.date = date;
		this.text = text;
		this.cell = cell;
	}

	public static CellMemo create(LocalDate date, String text, Cell cell) {
		return CellMemo.builder()
			.date(date)
			.text(text)
			.cell(cell)
			.build();
	}
}
