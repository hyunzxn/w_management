package com.windstorm.management.implement.notification;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.domain.notification.Notification;

@ExtendWith(MockitoExtension.class)
class NotificationModifierTest {
	@Mock
	private NotificationReader notificationReader;

	@InjectMocks
	private NotificationModifier notificationModifier;

	@Test
	@DisplayName("알림의 isRead 상태가 false에서 true로 변경된다.")
	void updateNotificationIsRead() {
		// given
		Long id = 1L;
		Notification notification = mock(Notification.class);

		when(notificationReader.read(any(Long.class))).thenReturn(notification);

		// when
		notificationModifier.updateIsRead(id);

		// then
		verify(notificationReader, times(1)).read(any(Long.class));
		verify(notification, times(1)).updateIsRead();
	}
}