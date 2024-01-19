package com.windstorm.management.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.windstorm.management.api.user.report.request.ReportCreate;
import com.windstorm.management.api.user.report.response.ReportResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.report.Report;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.implement.notification.NotificationAppender;
import com.windstorm.management.implement.report.ReportAppender;
import com.windstorm.management.implement.report.ReportModifier;
import com.windstorm.management.implement.report.ReportReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final ReportAppender reportAppender;
	private final ReportReader reportReader;
	private final ReportModifier reportModifier;
	private final MemberReader memberReader;
	private final NotificationAppender notificationAppender;

	public void append(String uniqueId, ReportCreate request) {
		Member member = memberReader.read(uniqueId);
		reportAppender.append(member, request);
		notificationAppender.append(member.getName(), request.division());
	}

	public List<ReportResponse> getUnReadAllReports(Division division) {
		return reportReader.getUnReadAllReports(division);
	}

	public ReportResponse getReport(Long id) {
		Report report = reportReader.read(id);
		reportModifier.updateIsReadState(report);
		return ReportResponse.toResponse(report);
	}
}
