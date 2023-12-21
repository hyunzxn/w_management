package com.windstorm.management.api.user.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.user.auth.request.Login;
import com.windstorm.management.api.user.auth.request.Signup;
import com.windstorm.management.api.user.member.response.MemberResponse;
import com.windstorm.management.infrastructure.security.jwt.JwtResponse;
import com.windstorm.management.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ApiResponse<MemberResponse> create(@Valid @RequestBody Signup request) {
		return ApiResponse.of(HttpStatus.CREATED, "새로운 청년 추가가 완료되었습니다.", authService.create(request));
	}

	@PostMapping("/login")
	public ApiResponse<JwtResponse> login(@Valid @RequestBody Login request) {
		return ApiResponse.of(HttpStatus.OK, "로그인에 성공하였습니다.", authService.login(request));
	}
}
