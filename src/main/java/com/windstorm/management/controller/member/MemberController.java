package com.windstorm.management.controller.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.controller.global.ApiResponse;
import com.windstorm.management.controller.member.request.MemberCreate;
import com.windstorm.management.controller.member.request.MemberLogin;
import com.windstorm.management.controller.member.response.MemberResponse;
import com.windstorm.management.service.member.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

	private final MemberService memberService;

	@PostMapping()
	public ApiResponse<MemberResponse> create(@Valid @RequestBody MemberCreate request) {
		return ApiResponse.of(HttpStatus.CREATED, "새로운 청년 추가가 완료되었습니다.", memberService.create(request));
	}

	@PostMapping("/login")
	public ApiResponse<MemberResponse> login(@Valid @RequestBody MemberLogin request) {
		return ApiResponse.of(HttpStatus.OK, "로그인에 성공하였습니다.", memberService.login(request));
	}
}
