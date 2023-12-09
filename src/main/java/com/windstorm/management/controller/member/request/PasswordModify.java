package com.windstorm.management.controller.member.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record PasswordModify(

	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$",
		message = "비밀번호는 영문, 숫자 조합 8 ~ 20 자리 이상이어야 합니다.")
	String password
) {
}
