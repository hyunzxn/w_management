package com.windstorm.management.service.report;

import org.springframework.stereotype.Service;

import com.windstorm.management.api.user.report.request.ReportCreate;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.implement.report.ReportAppender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final ReportAppender reportAppender;
	private final MemberReader memberReader;

	public void append(String uniqueId, ReportCreate request) {
		Member member = memberReader.read(uniqueId);
		reportAppender.append(member, request);
	}
}
