package com.windstorm.management.domain.report;

import java.time.LocalDate;

import com.windstorm.management.domain.BaseTimeEntity;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Report extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	private String targetName;

	@Lob
	private String content;

	@Enumerated(EnumType.STRING)
	private Division division;

	@Lob
	private String specialNote;

	@Lob
	private String targetPrayRequest;

	private boolean isRead;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	private Report(LocalDate date, String targetName, String content, Division division, String specialNote,
		String targetPrayRequest, Member member) {
		this.date = date;
		this.targetName = targetName;
		this.content = content;
		this.division = division;
		this.specialNote = specialNote;
		this.targetPrayRequest = targetPrayRequest;
		this.member = member;
		this.isRead = false;
	}

	public static Report create(LocalDate date, String targetName, String content, Division division, String specialNote,
		String targetPrayRequest, Member member) {
		return Report.builder()
			.date(date)
			.targetName(targetName)
			.content(content)
			.division(division)
			.specialNote(specialNote)
			.targetPrayRequest(targetPrayRequest)
			.member(member)
			.build();
	}

	public void updateIsReadState() {
		this.isRead = true;
	}
}
