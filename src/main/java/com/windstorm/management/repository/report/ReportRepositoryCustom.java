package com.windstorm.management.repository.report;

import java.util.List;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.report.Report;

public interface ReportRepositoryCustom {
	List<Report> findAllUnReadReports(Division division);
}
