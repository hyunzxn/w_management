package com.windstorm.management.implement.notification;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.domain.notification.Notification;
import com.windstorm.management.implement.member.MemberReader;
import com.windstorm.management.repository.notification.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationAppender {
	private final NotificationRepository notificationRepository;
	private final MemberReader memberReader;

	@Transactional
	public void append(String writerName, Division division) {
		Member pastor = memberReader.findDivisionPastor(division);
		Notification notification = Notification.createNotification(
			writerName + " 청년이 심방 보고서를 작성했습니다.",
			pastor
		);
		notificationRepository.save(notification);
	}
}
