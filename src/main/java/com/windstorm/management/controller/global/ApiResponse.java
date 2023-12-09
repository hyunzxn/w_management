package com.windstorm.management.controller.global;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private final HttpStatus httpStatus;
	private final String message;
	private final T body;

	private ApiResponse(HttpStatus httpStatus, String message, T body) {
		this.httpStatus = httpStatus;
		this.message = message;
		this.body = body;
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T body) {
		return new ApiResponse<>(httpStatus, message, body);
	}
}
