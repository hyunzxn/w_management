package com.windstorm.management.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windstorm.management.domain.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
