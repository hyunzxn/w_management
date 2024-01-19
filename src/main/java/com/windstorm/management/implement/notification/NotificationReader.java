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

	public Notification read(Long id) {
		return notificationRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(id + "에 해당하는 알림이 존재하지 않습니다."));
	}

	public List<Notification> readBy(Long id) {
		return notificationRepository.findNotificationsByPastor(id);
	}
}
