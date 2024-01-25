package com.windstorm.management.api.user.attendance.request;

import java.time.LocalDate;

import com.windstorm.management.domain.attendance.Attendance;

import lombok.Builder;

@Builder
public record AttendanceCreate(
	LocalDate date,
	String uniqueId,
	boolean isWorship,
	boolean isCellMeeting,
	boolean isEveningWorship
) {

	public Attendance toAttendance() {
		if (this.uniqueId == null || this.uniqueId.isEmpty()) {
			throw new IllegalArgumentException("출석을 등록하려는 청년의 교적번호를 입력해주세요."); // note 일단 Validation 라이브러리 안 쓰는 걸로 한 번 시도
		}
		return Attendance.createAttendance(
			this.date,
			this.isWorship,
			this.isCellMeeting,
			this.isEveningWorship
		);
	}
}
