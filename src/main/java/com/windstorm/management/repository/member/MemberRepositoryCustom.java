package com.windstorm.management.repository.member;

import java.util.List;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.member.Member;

public interface MemberRepositoryCustom {

	List<Member> findAllByName(String name);

	Member findDivisionPastor(Division division);
}
