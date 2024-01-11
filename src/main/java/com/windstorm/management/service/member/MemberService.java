package com.windstorm.management.service.member;

import java.util.List;

import org.springframework.stereotype.Service;

import com.windstorm.management.api.admin.member.request.MemberCellModify;
import com.windstorm.management.api.admin.member.request.MemberModify;
import com.windstorm.management.api.user.member.request.PasswordModify;
import com.windstorm.management.api.user.member.response.MemberResponse;
import com.windstorm.management.domain.cell.Cell;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.implement.cell.CellReader;
import com.windstorm.management.implement.member.MemberModifier;
import com.windstorm.management.implement.member.MemberReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberReader memberReader;
	private final MemberModifier memberModifier;
	private final CellReader cellReader;

	/**
	 * for Admin
	 */
	public MemberResponse modify(MemberModify request) {
		Member member = memberReader.read(request.uniqueId());
		memberModifier.modify(member, request);
		return MemberResponse.toResponse(member);
	}

	/**
	 * for Admin
	 */
	public void modifyCell(MemberCellModify request) {
		Member member = memberReader.read(request.uniqueId());
		Cell newCell = cellReader.readWithDivision(request.cellName(), request.division());
		memberModifier.modifyCell(member, newCell);
	}

	public List<MemberResponse> get(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("이름을 입력해주세요.");
		}
		List<Member> members = memberReader.readByName(name);
		return members.stream()
			.map(MemberResponse::toResponse)
			.toList();
	}

	public void modifyPassword(String uniqueId, PasswordModify request) {
		Member member = memberReader.read(uniqueId);
		memberModifier.modifyPassword(member, request);
	}
}
