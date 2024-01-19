package com.windstorm.management.implement.notification;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.notification.Notification;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationModifier {
	private final NotificationReader notificationReader;

	@Transactional
	public void updateIsRead(Notification notification) {
		notification.updateIsRead();
	}
}
