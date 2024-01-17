package com.windstorm.management.repository.notification;

import java.util.List;

import com.windstorm.management.domain.notification.Notification;

public interface NotificationRepositoryCustom {

	List<Notification> findNotificationsByPastor(Long id);
}
