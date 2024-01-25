package com.windstorm.management.service.attendance;

import org.springframework.stereotype.Service;

import com.windstorm.management.domain.attendance.Attendance;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.attendance.AttendanceAppender;
import com.windstorm.management.implement.member.MemberReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {
	private final AttendanceAppender attendanceAppender;
	private final MemberReader memberReader;

	public void append(Attendance attendance, String uniqueId) {
		Member member = memberReader.read(uniqueId);
		attendanceAppender.append(attendance, member);
	}
}
