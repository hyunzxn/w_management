package com.windstorm.management.service.report;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.api.user.report.request.ReportCreate;
import com.windstorm.management.api.user.report.response.ReportResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.report.Report;
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

	@ParameterizedTest
	@CsvSource({"DANIEL, 10", "JOSEPH1, 2"})
	@DisplayName("사역자가 자신의 청의 읽지 않은 심방 보고서를 조회할 수 있다.")
	void getUnReadAllReports(Division division, int expected) {
		// given
		Member member = createMember();
		memberRepository.save(member);

		// 다니엘청 보고서 10개, 요셉1청 보고서 2개를 담은 List<Report>를 만든다.
		List<Report> reports = new java.util.ArrayList<>(IntStream.rangeClosed(1, 10)
			.mapToObj(i ->
				createReport(
					LocalDate.of(2024, 1, i),
					"Name" + i,
					"Content" + i,
					Division.DANIEL,
					"SpecialNote" + i,
					"PrayRequest" + i,
					member
				)
			).toList());
		Report josephReport1 = createReport(
			LocalDate.of(2024, 1, 15),
			"김민영",
			"민영이 심방 내용",
			Division.JOSEPH1,
			"민영이 특이 사항",
			"민영이 기도 제목",
			member
		);
		Report josephReport2 = createReport(
			LocalDate.of(2024, 1, 19),
			"박민욱",
			"민욱이 심방 내용",
			Division.JOSEPH1,
			"민욱이 특이 사항",
			"민욱이 기도 제목",
			member
		);
		reports.add(josephReport1);
		reports.add(josephReport2);
		reportRepository.saveAll(reports);

		// when
		List<ReportResponse> result = reportService.getUnReadAllReports(division);

		// then
		assertThat(result).hasSize(expected);
	}

	@Test
	@DisplayName("사역자 읽은 심방 보고서의 조회 여부가 true로 업데이트 된다.")
	@Transactional
	void getReport() {
		// given
		Member member = createMember();
		memberRepository.save(member);

		Report report = createReport(
			LocalDate.of(2024, 1, 6),
			"서인국",
			"심방 내용입니다.",
			Division.DANIEL,
			"특이사항입니다.",
			"기도제목 입니다.",
			member
		);
		reportRepository.save(report);

		// when
		reportService.getReport(report.getId());

		// then
		assertThat(report.isRead()).isTrue();
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

	private Report createReport(LocalDate date, String targetName, String content, Division division,
		String specialNote, String targetPrayRequest, Member member) {
		return Report.create(
			date,
			targetName,
			content,
			division,
			specialNote,
			targetPrayRequest,
			member
		);
	}
}