package com.windstorm.management.controller.admin.member;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.controller.admin.member.request.MemberModify;
import com.windstorm.management.controller.global.ApiResponse;
import com.windstorm.management.controller.member.response.MemberResponse;
import com.windstorm.management.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {
	private final MemberService memberService;

	@PreAuthorize("isAuthenticated()")
	@PutMapping("/modify")
	public ApiResponse<MemberResponse> modify(@RequestParam String uniqueId, @RequestBody MemberModify request) {
		return ApiResponse.of(HttpStatus.OK, "Member 정보 수정이 완료됐습니다.", memberService.modify(uniqueId, request));
	}
}
