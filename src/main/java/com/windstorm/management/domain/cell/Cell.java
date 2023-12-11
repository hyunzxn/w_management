package com.windstorm.management.domain.cell;

import java.util.ArrayList;
import java.util.List;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.member.Member;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cell {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private Division division;

	@OneToMany(mappedBy = "cell", cascade = CascadeType.ALL)
	private List<Member> members = new ArrayList<>();

	@Builder
	private Cell(String name, Division division) {
		this.name = name;
		this.division = division;
	}

	public static Cell create(String name, Division division) {
		return Cell.builder()
			.name(name)
			.division(division)
			.build();
	}

	public void addMember(Member member) {
		member.defineCell(this);
		this.members.add(member);
	}
}