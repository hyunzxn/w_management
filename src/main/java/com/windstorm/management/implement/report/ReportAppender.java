package com.windstorm.management.implement.report;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.api.user.report.request.ReportCreate;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.report.Report;
import com.windstorm.management.repository.report.ReportRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportAppender {
	private final ReportRepository reportRepository;

	@Transactional
	public void append(Member member, ReportCreate request) {
		Report newReport = Report.create(
			request.date(),
			request.targetName(),
			request.content(),
			request.division(),
			request.specialNote(),
			request.targetPrayRequest(),
			member
		);
		reportRepository.save(newReport);
		log.info("새로운 심방 보고서 작성 완료. 보고서 id={}", newReport.getId());
	}
}
