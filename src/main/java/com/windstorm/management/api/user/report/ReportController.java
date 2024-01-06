package com.windstorm.management.api.user.report;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.user.report.request.ReportCreate;
import com.windstorm.management.infrastructure.security.UserPrincipal;
import com.windstorm.management.service.report.ReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {
	private final ReportService reportService;

	@PreAuthorize("isAuthenticated() && hasAnyRole('HEAD_MANAGER', 'MANAGER', 'CAPTAIN')")
	@PostMapping()
	public ApiResponse<String> append(@Valid @RequestBody ReportCreate request,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		String uniqueId = userPrincipal.getUniqueId();
		reportService.append(uniqueId, request);
		return ApiResponse.of(HttpStatus.CREATED, "심방 보고서 작성 성공", "심방 보고서 작성을 완료했습니다.");
	}
}
