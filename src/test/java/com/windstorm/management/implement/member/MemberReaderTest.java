package com.windstorm.management.implement.member;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.windstorm.management.domain.global.Division;
import com.windstorm.management.domain.global.Gender;
import com.windstorm.management.domain.global.LeaderRole;
import com.windstorm.management.domain.member.Member;
import com.windstorm.management.repository.member.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberReaderTest {
	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private MemberReader memberReader;

	@Test
	@DisplayName("유저의 고유한 교적번호로 유저를 조회할 수 있다.")
	void readByUniqueIdSuccess() {
		// given
		Member member = createMember();
		String uniqueId = "1";

		when(memberRepository.findByUniqueId(any(String.class))).thenReturn(Optional.of(member));

		// when
		Member result = memberReader.read(uniqueId);

		// then
		assertThat(result.getUniqueId()).isEqualTo("1");
		assertThat(result.getName()).isEqualTo("김철수");
		assertThat(result.getDivision()).isEqualTo(Division.DANIEL);
		assertThat(result.getGender()).isEqualTo(Gender.MALE);
		assertThat(result.getRole()).isEqualTo(LeaderRole.CAPTAIN);
		assertThat(result.getPhoneNumber()).isEqualTo("010-1234-5678");
		assertThat(result.getAddress()).isEqualTo("경기도 성남시");
	}

	@Test
	@DisplayName("존재하지 않는 교적번호에 해당하는 유저를 조회할 수 없다.")
	void readByUniqueIdFail() {
		// given
		String uniqueId = "100";

		when(memberRepository.findByUniqueId(any(String.class))).thenThrow(
			new RuntimeException("교적번호: " + uniqueId + "에 해당하는 데이터가 존재하지 않습니다."));

		// when, then
		assertThatThrownBy(() -> memberReader.read(uniqueId))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("교적번호: " + uniqueId + "에 해당하는 데이터가 존재하지 않습니다.");
	}

	@Test
	@DisplayName("유저의 이름으로 유저를 조회할 수 있다.")
	void readByNameSuccess() {
		// given
		String name = "김철수";
		List<Member> members = List.of(createMember());

		when(memberRepository.findAllByName(any(String.class))).thenReturn(members);

		// when
		List<Member> result = memberReader.readByName(name);

		// then
		assertThat(result)
			.hasSize(1)
			.extracting("uniqueId", "name", "division")
			.containsExactlyInAnyOrder(
				tuple("1", "김철수", Division.DANIEL)
			);
	}

	@Test
	@DisplayName("찾으려고 하는 이름에 해당하는 유저가 존재하지 않아 조회에 실패한다.")
	void readByNameFail() {
		// given
		String name = "홍길동";

		when(memberRepository.findAllByName(any(String.class))).thenThrow(
			new RuntimeException(name + "에 해당하는 데이터가 존재하지 않습니다."));

		// when, then
		assertThatThrownBy(() -> memberReader.readByName(name))
			.isInstanceOf(RuntimeException.class)
			.hasMessage(name + "에 해당하는 데이터가 존재하지 않습니다.");
	}

	private Member createMember() {
		return Member.create(
			"1",
			"김철수",
			"saeeden1234!!",
			LocalDate.of(2000, 11, 20),
			Division.DANIEL,
			Gender.MALE,
			LeaderRole.CAPTAIN,
			"010-1234-5678",
			"경기도 성남시"
		);
	}
}