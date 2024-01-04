package com.windstorm.management.api.admin.member;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.admin.member.request.MemberCellModify;
import com.windstorm.management.api.admin.member.request.MemberModify;
import com.windstorm.management.api.user.member.response.MemberResponse;
import com.windstorm.management.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {
	private final MemberService memberService;

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@PutMapping("/modify")
	public ApiResponse<MemberResponse> modify(@RequestBody MemberModify request) {
		return ApiResponse.of(HttpStatus.OK, "Member 정보 수정이 완료됐습니다.", memberService.modify(request));
	}

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@PutMapping("/modify/cell")
	public ApiResponse<Void> modifyCell(@RequestBody MemberCellModify request) {
		memberService.modifyCell(request);
		return ApiResponse.of(HttpStatus.OK, "Member의 셀 변경이 완료됐습니다.", null);
	}
}
