package com.windstorm.management.controller.admin.cell;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.controller.cell.request.CellAddMember;
import com.windstorm.management.controller.cell.request.CellCreate;
import com.windstorm.management.controller.cell.response.CellResponse;
import com.windstorm.management.controller.global.ApiResponse;
import com.windstorm.management.service.cell.CellService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/cells")
@RequiredArgsConstructor
public class AdminCellController {
	private final CellService cellService;

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<CellResponse> append(@Valid @RequestBody CellCreate request) {
		return ApiResponse.of(HttpStatus.CREATED, "새로운 셀 생성에 성공하였습니다.", cellService.append(request));
	}

	@PostMapping("/members")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Object> addMember(@RequestBody CellAddMember request) {
		cellService.addMember(request);
		return ApiResponse.of(HttpStatus.OK, "새로운 셀원이 추가되었습니다.", null);
	}
}
