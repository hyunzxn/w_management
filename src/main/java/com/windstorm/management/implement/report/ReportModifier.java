package com.windstorm.management.implement.report;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.report.Report;

@Component
public class ReportModifier {

	@Transactional
	public void updateIsReadState(Report report) {
		report.updateIsReadState();
	}
}
