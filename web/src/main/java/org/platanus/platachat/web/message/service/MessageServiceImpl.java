package org.platanus.platachat.web.message.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.platanus.platachat.web.message.model.MessagePayload;
import org.platanus.platachat.web.message.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
	
	private final MessageRepository messageRepository;
	
	@Override
	public List<MessagePayload> getChatLogs(String roomId) {
		if (StringUtils.isBlank(roomId)) {
			throw new IllegalArgumentException("방 정보가 비어 있습니다.");
		}
		return messageRepository.findByRoomId(roomId);
	}
	
	@Override
	public Page<MessagePayload> getChatLogsByPaging(String roomId, int page) {
		final int PAGE_SIZE = 50;
		return getChatLogsByPaging(roomId, page, PAGE_SIZE);
	}
	
	@Override
	public Page<MessagePayload> getChatLogsByPaging(String roomId, int page, int size) {
		final int MESSAGE_COUNT = messageRepository.countByRoomId(roomId);
		return messageRepository.findByRoomId(roomId, PageRequest.of(page, size));
	}
	
	@Override
	public MessagePayload saveMessage(MessagePayload message) {
		return messageRepository.save(message);
	}
}