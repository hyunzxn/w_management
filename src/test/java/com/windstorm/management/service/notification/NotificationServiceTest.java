package com.windstorm.management.service.notification;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
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

import com.windstorm.management.api.admin.notification.response.NotificationResponse;
import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.notification.Notification;
import com.windstorm.management.repository.member.MemberRepository;
import com.windstorm.management.repository.notification.NotificationRepository;

@SpringBootTest
@ActiveProfiles("test")
class NotificationServiceTest {
	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private NotificationService notificationService;

	@AfterEach
	void cleanUp() {
		notificationRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("사역자가 자신의 청의 읽지 않은 심방 보고서를 조회할 수 있다.")
	void getNotifications() {
		// given
		Member pastor1 = createMember("1", "이준영", Division.DANIEL);
		Member pastor2 = createMember("2", "김종은", Division.JOSEPH2);
		memberRepository.save(pastor1);
		memberRepository.save(pastor2);

		List<Notification> danielNotifications = IntStream.rangeClosed(1, 3)
			.mapToObj(i ->
				createNotification(
					i + "번째 심방 보고서가 등록되었습니다.",
					pastor1
				)
			).toList();
		notificationRepository.saveAll(danielNotifications);

		// when
		List<NotificationResponse> result1 = notificationService.getNotifications(pastor1.getUniqueId());
		List<NotificationResponse> result2 = notificationService.getNotifications(pastor2.getUniqueId());

		// then
		assertThat(result1).hasSize(3);
		assertThat(result2).hasSize(0);
	}

	@Test
	@DisplayName("알림의 읽음 상태가 false에서 true로 업데이트 된다.")
	@Transactional
	void updateNotificationIsRead() {
		// given
		Member pastor = createMember("1", "이준영", Division.DANIEL);
		memberRepository.save(pastor);

		Notification notification = createNotification("알림 내용입니다.", pastor);
		notificationRepository.save(notification);

		// when
		notificationService.updateNotificationIsRead(notification.getId());

		// then
		assertThat(notification.isRead()).isTrue();
	}

	private Member createMember(String uniqueId, String name, Division division) {
		return Member.builder()
			.uniqueId(uniqueId)
			.name(name)
			.division(division)
			.role(LeaderRole.PASTOR)
			.build();
	}

	private Notification createNotification(String message, Member member) {
		return Notification.builder()
			.message(message)
			.member(member)
			.build();
	}
}