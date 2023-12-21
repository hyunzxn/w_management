package com.windstorm.management.domain.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.windstorm.management.api.admin.member.request.MemberModify;
import com.windstorm.management.api.user.member.request.PasswordModify;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	private static final int ORIGIN_YEAR = 1972;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String uniqueId; // 교적번호

	private String name;

	private String password;

	private LocalDate birthDate;

	@Enumerated(EnumType.STRING)
	private Division division;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private LeaderRole role;

	private String phoneNumber;

	private String address;

	@ManyToOne
	@JoinColumn(name = "cell_id")
	private Cell cell;

	@Builder
	private Member(String uniqueId, String name, String password, LocalDate birthDate, Division division,
		Gender gender, LeaderRole role, String phoneNumber, String address) {
		this.uniqueId = uniqueId;
		this.name = name;
		this.password = password;
		this.birthDate = birthDate;
		this.division = division;
		this.gender = gender;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public static Member create(String uniqueId, String name, String password, LocalDate birthDate,
		Division division,
		Gender gender, LeaderRole role, String phoneNumber, String address) {
		return Member.builder()
			.uniqueId(uniqueId)
			.name(name)
			.password(password)
			.birthDate(birthDate)
			.division(division)
			.gender(gender)
			.role(role)
			.phoneNumber(phoneNumber)
			.address(address)
			.build();
	}

	public int calculateAgeGroup(LocalDate birthDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return Integer.parseInt(birthDate.format(formatter).substring(0, 4)) - ORIGIN_YEAR;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

	public void modifyPassword(PasswordModify request) {
		this.password = request.password();
	}

	public void defineCell(Cell cell) {
		this.cell = cell;
	}

	public void modify(MemberModify request) {
		this.uniqueId = request.uniqueId();
		this.name = request.name();
		this.birthDate = request.birthDate();
		this.division = request.division();
		this.gender = request.gender();
		this.role = request.role();
		this.phoneNumber = request.phoneNumber();
		this.address = request.address();
	}
}
