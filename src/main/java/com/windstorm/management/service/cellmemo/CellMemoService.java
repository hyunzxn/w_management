package com.windstorm.management.service.cellmemo;

import org.springframework.stereotype.Service;

import com.windstorm.management.api.user.cellmemo.request.CellMemoCreate;
import com.windstorm.management.implement.cellmemo.CellMemoAppender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CellMemoService {
	private final CellMemoAppender cellMemoAppender;

	public void append(CellMemoCreate request) {
		cellMemoAppender.append(request);
	}
}
