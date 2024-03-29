package com.windstorm.management.api.admin.notification;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windstorm.management.api.ApiResponse;
import com.windstorm.management.api.admin.notification.response.NotificationResponse;
import com.windstorm.management.infrastructure.security.UserPrincipal;
import com.windstorm.management.service.notification.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notifications")
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

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@PutMapping("/{id}")
	public ApiResponse<Object> updateNotificationIsRead(@PathVariable Long id) {
		notificationService.updateNotificationIsRead(id);
		return ApiResponse.of(HttpStatus.OK, "미확인 알림 조회 상태 업데이트 성공", null);
	}

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@DeleteMapping("/{id}")
	public ApiResponse<Object> deleteNotification(@PathVariable Long id) {
		notificationService.delete(id);
		return ApiResponse.of(HttpStatus.OK, "알림 삭제 성공", null);
	}

	@PreAuthorize("isAuthenticated() && hasAnyRole('PASTOR')")
	@GetMapping("/check")
	public ApiResponse<Boolean> checkUnreadNotificationIsPresent(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {
		String loginUserUniqueId = userPrincipal.getUniqueId();
		return ApiResponse.of(HttpStatus.OK, "미확인 알림 존재 여부 확인 성공",
			notificationService.checkUnreadNotificationIsPresent(loginUserUniqueId));
	}
}
