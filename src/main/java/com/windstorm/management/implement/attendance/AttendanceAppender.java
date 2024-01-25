package com.windstorm.management.implement.attendance;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.attendance.Attendance;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.attendance.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AttendanceAppender {
	private final AttendanceRepository attendanceRepository;

	@Transactional
	public void append(Attendance attendance, Member member) {
		attendance.linkMember(member);
		attendanceRepository.save(attendance);
	}
}
