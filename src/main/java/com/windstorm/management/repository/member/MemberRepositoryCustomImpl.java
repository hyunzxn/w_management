package com.windstorm.management.repository.member;

import static com.windstorm.management.domain.member.QMember.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.windstorm.management.domain.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Member> findAllByName(String name) {
		return queryFactory
			.selectFrom(member)
			.where(member.name.eq(name))
			.orderBy(member.id.asc())
			.fetch();
	}
}
