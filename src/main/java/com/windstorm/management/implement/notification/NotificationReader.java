package com.windstorm.management.implement.notification;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.notification.Notification;
import com.windstorm.management.repository.notification.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationReader {
	private final NotificationRepository notificationRepository;

	public List<Notification> readBy(Long id) {
		return notificationRepository.findNotificationsByPastor(id);
	}
}
