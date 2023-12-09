package com.windstorm.management.controller.member;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.controller.global.ApiResponse;
import com.windstorm.management.controller.member.request.PasswordModify;
import com.windstorm.management.controller.member.response.MemberResponse;
import com.windstorm.management.security.UserPrincipal;
import com.windstorm.management.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

	private final MemberService memberService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping()
	public ApiResponse<List<MemberResponse>> get(@RequestParam String name) {
		return ApiResponse.of(HttpStatus.OK, "청년 조회에 성공하였습니다.", memberService.get(name));
	}

	@PreAuthorize("isAuthenticated()")
	@PutMapping("/modify")
	public ApiResponse<Object> modify(@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody PasswordModify request) {
		String uniqueMemberId = userPrincipal.getUniqueMemberId();
		memberService.modify(uniqueMemberId, request);
		return ApiResponse.of(HttpStatus.OK, "비밀번호 변경이 완료됐습니다.", null);
	}
}
