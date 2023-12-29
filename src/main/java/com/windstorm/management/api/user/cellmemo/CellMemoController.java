package com.windstorm.management.api.user.cellmemo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.user.cellmemo.request.CellMemoCreate;
import com.windstorm.management.service.cellmemo.CellMemoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cell/memos")
public class CellMemoController {
	private final CellMemoService cellMemoService;

	@PostMapping()
	public ApiResponse<String> append(@Valid @RequestBody CellMemoCreate request) {
		cellMemoService.append(request);
		return ApiResponse.of(HttpStatus.CREATED, "셀 특이사항 입력 성공", "셀 특이사항 입력을 완료하였습니다.");
	}
}
