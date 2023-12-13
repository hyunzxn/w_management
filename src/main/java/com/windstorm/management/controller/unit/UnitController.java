package com.windstorm.management.controller.unit;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.controller.global.ApiResponse;
import com.windstorm.management.controller.unit.response.UnitResponse;
import com.windstorm.management.service.unit.UnitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {
	private final UnitService unitService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping()
	public ApiResponse<UnitResponse> get(@RequestParam String name) {
		return ApiResponse.of(HttpStatus.OK, "진 조회가 완료되었습니다.", unitService.get(name));
	}
}
