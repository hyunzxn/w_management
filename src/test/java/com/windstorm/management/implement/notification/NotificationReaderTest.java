package com.windstorm.management.implement.notification;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.repository.notification.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationReaderTest {
	@Mock
	private NotificationRepository notificationRepository;

	@InjectMocks
	private NotificationReader notificationReader;

	@Test
	@DisplayName("사역자별로 읽지 않은 알림을 확인할 수 있다.")
	void read() {
		// given
		Long id = 1L;

		// when
		notificationReader.readBy(id);

		// then
		verify(notificationRepository, times(1)).findNotificationsByPastor(any(Long.class));
	}

}