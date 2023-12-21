package com.windstorm.management.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice {

	/**
	 * 검증을 통과하지 못 할 때 발생하는 에러를 컨트롤
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiResponse<Object> bindException(BindException e) {
		log.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), e);
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
			null
		);
	}

	/**
	 * Runtime 실행 중 발생하는 에러를 컨트롤
	 * ex) DB Data정합성오류, DB 조회 시 데이터 없음 등
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public ApiResponse<Object> runtimeExceptionHandler(RuntimeException e) {
		log.error(e.getMessage(), e);
		return ApiResponse.of(
			HttpStatus.INTERNAL_SERVER_ERROR,
			e.getMessage(),
			null
		);
	}

	/**
	 * 요청을 보낼 때 값을 잘못되게 보내는 경우에 발생하는 에러를 컨트롤
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResponse<Object> illegalArgumentExceptionHandler(IllegalArgumentException e) {
		log.error(e.getMessage(), e);
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getMessage(),
			null
		);
	}
}
