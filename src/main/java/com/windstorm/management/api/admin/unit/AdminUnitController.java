package com.windstorm.management.api.admin.unit;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.admin.unit.request.UnitCreate;
import com.windstorm.management.api.user.unit.response.UnitResponse;
import com.windstorm.management.service.unit.UnitService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/units")
@RequiredArgsConstructor
public class AdminUnitController {
	private final UnitService unitService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping()
	public ApiResponse<UnitResponse> append(@Valid @RequestBody UnitCreate request) {
		return ApiResponse.of(HttpStatus.CREATED, "새로운 진 생성이 완료되었습니다.", unitService.append(request));
	}
}
