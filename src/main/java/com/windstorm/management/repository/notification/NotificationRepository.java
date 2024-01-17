package com.windstorm.management.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windstorm.management.domain.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {
}
