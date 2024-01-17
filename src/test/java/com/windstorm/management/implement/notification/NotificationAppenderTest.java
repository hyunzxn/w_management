package com.windstorm.management.implement.notification;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.notification.Notification;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.repository.notification.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationAppenderTest {
	@Mock
	private NotificationRepository notificationRepository;

	@Mock
	private MemberReader memberReader;

	@InjectMocks
	private NotificationAppender notificationAppender;

	@Test
	@DisplayName("청 사역자에게 알림을 등록할 수 있다.")
	void append() {
		// given
		String writerName = "지창욱";
		Division daniel = Division.DANIEL;

		// when
		notificationAppender.append(writerName, daniel);

		// then
		verify(memberReader, times(1)).findDivisionPastor(any(Division.class));
		verify(notificationRepository, times(1)).save(any(Notification.class));
	}
}