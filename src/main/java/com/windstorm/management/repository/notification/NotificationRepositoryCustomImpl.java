package com.windstorm.management.repository.notification;

import static com.windstorm.management.domain.notification.QNotification.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.windstorm.management.domain.notification.Notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Notification> findNotificationsByPastor(Long id) {
		return queryFactory
			.selectFrom(notification)
			.where(notification.member.id.eq(id).and(notification.isRead.eq(false)))
			.orderBy(notification.id.desc())
			.fetch();
	}
}
