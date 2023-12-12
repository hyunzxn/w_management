package com.windstorm.management.controller.cell;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.controller.cell.response.CellResponse;
import com.windstorm.management.controller.global.ApiResponse;
import com.windstorm.management.service.cell.CellService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cells")
@RequiredArgsConstructor
public class CellController {
	private final CellService cellService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping()
	public ApiResponse<CellResponse> get(@RequestParam String name) {
		return ApiResponse.of(HttpStatus.OK, "셀 조회에 성공하였습니다.", cellService.get(name));
	}
}
