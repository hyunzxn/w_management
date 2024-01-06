package com.windstorm.management.api.user.report.response;

import java.time.LocalDate;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.report.Report;

import lombok.Builder;

@Builder
public record ReportResponse(
	long id,
	LocalDate date,
	Division division,
	String targetName,
	String content,
	String specialNote,
	String targetPrayRequest,
	boolean isRead,
	String writerName
) {
	public static ReportResponse toResponse(Report report) {
		return ReportResponse.builder()
			.id(report.getId())
			.date(report.getDate())
			.division(report.getDivision())
			.targetName(report.getTargetName())
			.content(report.getContent())
			.specialNote(report.getSpecialNote())
			.targetPrayRequest(report.getTargetPrayRequest())
			.isRead(report.isRead())
			.writerName(report.getMember().getName())
			.build();
	}
}
