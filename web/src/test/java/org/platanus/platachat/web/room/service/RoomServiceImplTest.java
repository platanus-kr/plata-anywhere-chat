package org.platanus.platachat.web.room.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.platanus.platachat.web.member.dto.MemberJoinRequestDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.repository.RoomMemberRepository;
import org.platanus.platachat.web.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@SpringBootTest
class RoomServiceImplTest {
	//@Autowired
	//private MemberService memberService;
	//
	//@Autowired
	//private RoomService roomService;
	
	@BeforeEach
	void beforeEach() {
		//memberService.findAll().forEach(System.out::println);
		//List<MemberJoinRequestDto> lists = List.of(
		//		MemberJoinRequestDto.builder()
		//				.username("test1")
		//				.password("test1")
		//				.email("test1@test.com")
		//				.nickname("테스트1")
		//				.build(),
		//		MemberJoinRequestDto.builder()
		//				.username("test2")
		//				.password("test2")
		//				.email("test2@test.com")
		//				.nickname("테스트2")
		//				.build(),
		//		MemberJoinRequestDto.builder()
		//				.username("test3")
		//				.password("test3")
		//				.email("test3@test.com")
		//				.nickname("테스트3")
		//				.build(),
		//		MemberJoinRequestDto.builder()
		//				.username("test4")
		//				.password("test4")
		//				.email("test4@test.com")
		//				.nickname("테스트4")
		//				.build(),
		//		MemberJoinRequestDto.builder()
		//				.username("test5")
		//				.password("test5")
		//				.email("test5@test.com")
		//				.nickname("테스트5")
		//				.build(),
		//		MemberJoinRequestDto.builder()
		//				.username("test6")
		//				.password("test6")
		//				.email("test6@test.com")
		//				.nickname("테스트6")
		//				.build()
		//);
		//for (MemberJoinRequestDto dto : lists) {
		//	memberService.join(dto);
		//}
	}
	
	@Test
	void createRoom() {
		System.out.println("?");
		
		
		
	}
}