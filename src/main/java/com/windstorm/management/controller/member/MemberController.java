package com.windstorm.management.controller.member;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.controller.global.ApiResponse;
import com.windstorm.management.controller.member.request.PasswordModify;
import com.windstorm.management.security.UserPrincipal;
import com.windstorm.management.service.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

	private final MemberService memberService;

	@PreAuthorize("isAuthenticated()")
	@PutMapping("/modify")
	public ApiResponse<Object> modify(@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody PasswordModify request) {
		String uniqueMemberId = userPrincipal.getUniqueMemberId();
		memberService.modify(uniqueMemberId, request);
		return ApiResponse.of(HttpStatus.OK, "비밀번호 변경이 완료됐습니다.", null);
	}
}
