package org.platanus.platachat.web.chat.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.platanus.platachat.web.chat.model.MessagePayload;
import org.platanus.platachat.web.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	
	private final MessageRepository messageRepository;
	
	@Override
	public List<MessagePayload> getChatLogFromSimpleChat(String roomId) {
		if (StringUtils.isBlank(roomId)) {
			throw new IllegalArgumentException("방 정보가 비어 있습니다.");
		}
		return messageRepository.findByRoomId(roomId);
	}
	
	@Override
	public MessagePayload saveMessage(MessagePayload message) {
		return messageRepository.save(message);
	}
}
