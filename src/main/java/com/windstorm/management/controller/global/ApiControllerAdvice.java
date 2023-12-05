package com.windstorm.management.controller.global;

import org.springframework.dao.DataIntegrityViolationException;
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
	 * DB에서 데이터를 조회했을 때 이미 데이터가 존재하는 경우에 발생하는 에러를 컨트롤
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ApiResponse<Object> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
		log.error(e.getMessage(), e);
		return ApiResponse.of(
			HttpStatus.INTERNAL_SERVER_ERROR,
			e.getMessage(),
			null
		);
	}
}
