package com.windstorm.management.service.notification;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.windstorm.management.api.admin.notification.response.NotificationResponse;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.notification.Notification;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.implement.notification.NotificationModifier;
import com.windstorm.management.implement.notification.NotificationReader;
import com.windstorm.management.implement.notification.NotificationRemover;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationReader notificationReader;
	private final NotificationModifier notificationModifier;
	private final NotificationRemover notificationRemover;
	private final MemberReader memberReader;

	public List<NotificationResponse> getNotifications(String uniqueId) {
		Member pastor = memberReader.read(uniqueId);
		return notificationReader.readBy(pastor.getId()).stream()
			.map(NotificationResponse::toResponse)
			.collect(Collectors.toList());
	}

	public void updateNotificationIsRead(Long id) {
		Notification notification = notificationReader.read(id);
		notificationModifier.updateIsRead(notification);
	}

	public void delete(Long id) {
		Notification notification = notificationReader.read(id);
		notificationRemover.delete(notification);
	}

	/**
	 * @return true: 알림이 존재한다, false: 알림이 존재하지 않는다.
	 */
	public Boolean checkUnreadNotificationIsPresent(String uniqueId) {
		Member pastor = memberReader.read(uniqueId);
		List<Notification> notifications = notificationReader.readBy(pastor.getId());
		return !notifications.isEmpty();
	}
}
