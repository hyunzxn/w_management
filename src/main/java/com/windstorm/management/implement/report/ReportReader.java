package com.windstorm.management.implement.report;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.api.user.report.response.ReportResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.repository.report.ReportRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportReader {
	private final ReportRepository reportRepository;

	public List<ReportResponse> getUnReadAllReports(Division division) {
		return reportRepository.findAllUnReadReports(division).stream()
			.map(ReportResponse::toResponse)
			.toList();
	}
}
