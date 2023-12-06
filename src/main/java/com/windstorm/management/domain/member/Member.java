package com.windstorm.management.domain.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String uniqueMemberId; // 교적번호

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

	@Builder
	private Member(String uniqueMemberId, String name, String password, LocalDate birthDate, Division division,
		Gender gender, LeaderRole role, String phoneNumber, String address) {
		this.uniqueMemberId = uniqueMemberId;
		this.name = name;
		this.password = password;
		this.birthDate = birthDate;
		this.division = division;
		this.gender = gender;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public static Member create(String uniqueMemberId, String name, String password, LocalDate birthDate,
		Division division,
		Gender gender, LeaderRole role, String phoneNumber, String address) {
		return Member.builder()
			.uniqueMemberId(uniqueMemberId)
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
}
