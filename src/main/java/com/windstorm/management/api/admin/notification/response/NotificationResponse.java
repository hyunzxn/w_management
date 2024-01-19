package com.windstorm.management.api.admin.notification.response;

import com.windstorm.management.domain.notification.Notification;

import lombok.Builder;

@Builder
public record NotificationResponse(
	long id,
	String message
) {

	public static NotificationResponse toResponse(Notification notification) {
		return NotificationResponse.builder()
			.id(notification.getId())
			.message(notification.getMessage())
			.build();
	}
}
