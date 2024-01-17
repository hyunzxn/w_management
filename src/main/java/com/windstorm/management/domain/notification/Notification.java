package com.windstorm.management.domain.notification;

import com.windstorm.management.domain.BaseTimeEntity;
import com.windstorm.management.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String message;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private boolean isRead;

	@Builder
	private Notification(String message, Member member) {
		this.message = message;
		this.member = member;
		this.isRead = false;
	}

	public static Notification createNotification(String message, Member member) {
		return Notification.builder()
			.message(message)
			.member(member)
			.build();
	}
}
