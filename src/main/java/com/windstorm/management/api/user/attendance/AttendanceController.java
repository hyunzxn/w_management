package com.windstorm.management.api.user.attendance;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.user.attendance.request.AttendanceCreate;
import com.windstorm.management.service.attendance.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendances")
public class AttendanceController {
	private final AttendanceService attendanceService;

	@PreAuthorize("isAuthenticated() && hasAnyRole('CAPTAIN')")
	@PostMapping()
	public ApiResponse<String> append(@RequestBody AttendanceCreate request) {
		attendanceService.append(request.toAttendance(), request.uniqueId());
		return ApiResponse.of(HttpStatus.CREATED, "출석 체크 완료", "출석 체크를 완료했습니다.");
	}
}
