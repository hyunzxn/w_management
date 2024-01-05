package com.windstorm.management.api.admin.cell;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.admin.cell.request.CellAddMember;
import com.windstorm.management.api.admin.cell.request.CellCreate;
import com.windstorm.management.api.admin.cell.request.UnitModify;
import com.windstorm.management.api.user.cell.response.CellResponse;
import com.windstorm.management.service.cell.CellService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/cells")
@RequiredArgsConstructor
public class AdminCellController {
	private final CellService cellService;

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@PostMapping
	public ApiResponse<CellResponse> append(@Valid @RequestBody CellCreate request) {
		return ApiResponse.of(HttpStatus.CREATED, "새로운 셀 생성에 성공하였습니다.", cellService.append(request));
	}

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@PostMapping("/members")
	public ApiResponse<Object> addMember(@Valid @RequestBody CellAddMember request) {
		cellService.addMember(request);
		return ApiResponse.of(HttpStatus.OK, "새로운 셀원이 추가되었습니다.", null);
	}

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@PutMapping("/modify/unit")
	public ApiResponse<Object> modifyUnit(@RequestBody UnitModify request) {
		cellService.modifyUnit(request);
		return ApiResponse.of(HttpStatus.OK, "진 변경이 완료되었습니다.", null);
	}
}
