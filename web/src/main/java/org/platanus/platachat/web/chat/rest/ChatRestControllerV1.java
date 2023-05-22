package org.platanus.platachat.web.chat.rest;

import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.message.model.MessagePayload;
import org.platanus.platachat.web.message.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/chat/log")
@RequiredArgsConstructor
public class ChatRestControllerV1 {
	
	private final MessageService chatService;
	
	@GetMapping("/simple/{roomId}")
	public Page<MessagePayload> getChatLogSimple(@PathVariable(value = "roomId") String roomId,
												 @HasMember SessionMemberDto member,
												 @RequestParam(value = "page", defaultValue = "1") int page) {
		if (page > 0) {
			page -= 1;
		}
		return chatService.getChatLogsByPaging(roomId, page);
	}
}
