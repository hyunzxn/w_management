package com.windstorm.management.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtResponse {
	private String grantType;
	private String accessToken;
	private String refreshToken;
}
