package com.windstorm.management.api.user.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record Login(
	@NotBlank(message = "교적 번호를 입력해주세요")
	String uniqueMemberId,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$",
		message = "비밀번호를 다시 확인해주세요!")
	String password
) {
}
