package com.windstorm.management.implement.notification;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.notification.Notification;
import com.windstorm.management.repository.notification.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationRemover {
	private final NotificationRepository notificationRepository;

	@Transactional
	public void delete(Notification notification) {
		notificationRepository.delete(notification);
	}
}
