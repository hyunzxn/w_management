package com.windstorm.management.repository.report;

import static com.windstorm.management.domain.report.QReport.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.report.Report;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Report> findAllUnReadReports(Division division) {
		return queryFactory
			.selectFrom(report)
			.where(report.division.eq(division).and(report.isRead.eq(false)))
			.orderBy(report.id.desc())
			.fetch();
	}
}
