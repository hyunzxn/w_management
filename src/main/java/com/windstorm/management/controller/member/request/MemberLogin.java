package com.windstorm.management.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberLogin(
	@NotBlank(message = "교적 번호를 입력해주세요")
	String uniqueMemberId,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password
) {
}
