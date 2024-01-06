package com.windstorm.management.implement.report;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.api.user.report.request.ReportCreate;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.report.Report;
import com.windstorm.management.repository.report.ReportRepository;

@ExtendWith(MockitoExtension.class)
class ReportAppenderTest {
	@Mock
	private ReportRepository reportRepository;

	@InjectMocks
	private ReportAppender reportAppender;

	@Test
	@DisplayName("심방 보고서를 작성할 수 있다.")
	void append() {
		// given
		Member member = createMember();
		ReportCreate request = ReportCreate.builder()
			.date(LocalDate.of(2024, 1, 6))
			.targetName("김짱구")
			.content("심방 내용")
			.division(Division.DANIEL)
			.specialNote("특이사항")
			.targetPrayRequest("기도제목")
			.build();

		// when
		reportAppender.append(member, request);

		// then
		verify(reportRepository, times(1)).save(any(Report.class));
	}

	private Member createMember() {
		return Member.create(
			"1",
			"김철수",
			"saeeden1234!!",
			LocalDate.of(2000, 11, 20),
			Division.DANIEL,
			Gender.MALE,
			LeaderRole.CAPTAIN,
			"010-1234-5678",
			"경기도 성남시"
		);
	}
}