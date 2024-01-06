package com.windstorm.management.implement.report;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.report.ReportRepository;

@ExtendWith(MockitoExtension.class)
class ReportReaderTest {
	@Mock
	private ReportRepository reportRepository;

	@InjectMocks
	private ReportReader reportReader;

	@Test
	@DisplayName("findAllUnReadReports() 메서드가 한 번 이상 호출된다.")
	void getUnReadAllReports() {
		// given
		Member member = createMember();
		Division division = member.getDivision();

		// when
		reportReader.getUnReadAllReports(division);

		// then
		verify(reportRepository, times(1)).findAllUnReadReports(any(Division.class));
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