package com.windstorm.management.domain.attendance;

import java.time.LocalDate;

import com.windstorm.management.domain.BaseTimeEntity;
import com.windstorm.management.domain.member.Member;

import jakarta.persistence.Entity;
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
public class Attendance extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private boolean isWorship;

	private boolean isCellMeeting;

	private boolean isEveningWorship;

	@Builder
	private Attendance(LocalDate date, boolean isWorship, boolean isCellMeeting, boolean isEveningWorship) {
		this.date = date;
		this.isWorship = isWorship;
		this.isCellMeeting = isCellMeeting;
		this.isEveningWorship = isEveningWorship;
	}

	public static Attendance createAttendance(
		LocalDate date,
		boolean isWorship,
		boolean isCellMeeting,
		boolean isEveningWorship
	) {
		return Attendance.builder()
			.date(date)
			.isWorship(isWorship)
			.isCellMeeting(isCellMeeting)
			.isEveningWorship(isEveningWorship)
			.build();
	}

	public void linkMember(Member member) {
		this.member = member;
	}
}
