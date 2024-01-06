package com.windstorm.management.service.report;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.windstorm.management.api.user.report.request.ReportCreate;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.member.MemberRepository;
import com.windstorm.management.repository.report.ReportRepository;

@SpringBootTest
@ActiveProfiles("test")
class ReportServiceTest {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private ReportService reportService;

	@AfterEach
	void cleanUp() {
		reportRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("심방 보고서를 작성할 수 있다.")
	void append() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		String uniqueId = member.getUniqueId();
		ReportCreate request = ReportCreate.builder()
			.date(LocalDate.of(2024, 1, 6))
			.targetName("김철수")
			.content("심방 내용")
			.division(Division.DANIEL)
			.specialNote("특이사항")
			.targetPrayRequest("기도제목")
			.build();

		// when
		reportService.append(uniqueId, request);

		// then
		assertThat(reportRepository.findAll().isEmpty()).isFalse();
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