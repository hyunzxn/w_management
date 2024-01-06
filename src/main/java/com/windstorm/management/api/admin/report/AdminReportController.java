package com.windstorm.management.api.admin.report;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.user.report.response.ReportResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.infrastructure.security.UserPrincipal;
import com.windstorm.management.service.report.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/reports")
public class AdminReportController {
	private final ReportService reportService;

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@GetMapping()
	public ApiResponse<List<ReportResponse>> getUnReadAllReports(
		@AuthenticationPrincipal UserPrincipal loginUser
	) {
		Division division = loginUser.getDivision();
		return ApiResponse.of(HttpStatus.OK, "심방 보고서 조회 성공", reportService.getUnReadAllReports(division));
	}

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@GetMapping("/read")
	public ApiResponse<ReportResponse> getReport(@RequestParam Long id) {
		return ApiResponse.of(HttpStatus.OK, "심방 보고서 읽기 성공", reportService.getReport(id));
	}
}
