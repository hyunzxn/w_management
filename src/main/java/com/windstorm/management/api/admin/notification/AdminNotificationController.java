package com.windstorm.management.api.admin.notification;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.admin.notification.response.NotificationResponse;
import com.windstorm.management.infrastructure.security.UserPrincipal;
import com.windstorm.management.service.notification.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class AdminNotificationController {
	private final NotificationService notificationService;

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@GetMapping()
	public ApiResponse<List<NotificationResponse>> getNotifications(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {
		String uniqueId = userPrincipal.getUniqueId();
		return ApiResponse.of(HttpStatus.OK, "알림 조회 성공", notificationService.getNotifications(uniqueId));
	}
}
