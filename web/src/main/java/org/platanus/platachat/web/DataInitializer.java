package org.platanus.platachat.web;


import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.platanus.platachat.web.chat.model.MessagePayload;
import org.platanus.platachat.web.chat.model.MessageType;
import org.platanus.platachat.web.chat.service.ChatService;
import org.platanus.platachat.web.constants.ConfigConstant;
import org.platanus.platachat.web.member.dto.MemberJoinRequestDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {
	
	private final MemberService memberService;
	private final ChatService chatService;
	
	@Value("${plataanywherechat.environment.profile}")
	private String profile;
	
	@PostConstruct
	public void init() {
		if (!profile.equals(ConfigConstant.PROPERTY_ENV_PROFILE_LOCAL)) {
			return;
		}
		
		List<MemberJoinRequestDto> lists = List.of(
				MemberJoinRequestDto.builder()
						.username("test1")
						.password("test1")
						.email("test1@test.com")
						.nickname("테스트1")
						.build(),
				MemberJoinRequestDto.builder()
						.username("test2")
						.password("test2")
						.email("test2@test.com")
						.nickname("테스트2")
						.build(),
				MemberJoinRequestDto.builder()
						.username("test3")
						.password("test3")
						.email("test3@test.com")
						.nickname("테스트3")
						.build(),
				MemberJoinRequestDto.builder()
						.username("test4")
						.password("test4")
						.email("test4@test.com")
						.nickname("테스트4")
						.build()
		);
		
		for (MemberJoinRequestDto dto : lists) {
			memberService.join(dto);
		}
		
		///
		
		//for(int i = 0; i <= 100; i++) {
		//	MessagePayload build = MessagePayload.builder()
		//			.message("테스트 " + i + "번째 메시지")
		//			.nickname("닉네임")
		//			.roomId("test")
		//			.type(MessageType.TEXT)
		//			.timestamp(LocalDateTime.now())
		//			.userId("test1")
		//			.build();
		//	chatService.saveMessage(build);
		//}
	}
	
}
