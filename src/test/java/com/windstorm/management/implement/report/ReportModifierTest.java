package com.windstorm.management.implement.report;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.report.Report;

@ExtendWith(MockitoExtension.class)
class ReportModifierTest {
	@InjectMocks
	private ReportModifier reportModifier;

	@Test
	@DisplayName("조회된 보고서는 isRead 상태가 true가 된다.")
	void updateIsReadState() {
		// given
		Report report = createReport();

		// when
		reportModifier.updateIsReadState(report);

		// then
		assertThat(report.isRead()).isTrue();
	}

	private Report createReport() {
		return Report.builder()
			.date(LocalDate.of(2024, 1, 6))
			.targetName("이민호")
			.content("심방 내용입니다.")
			.division(Division.DANIEL)
			.specialNote("특이사항입니다.")
			.targetPrayRequest("기도 제목입니다.")
			.build();
	}
}