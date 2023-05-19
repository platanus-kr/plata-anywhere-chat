package org.platanus.platachat.web.chat.service;

import java.util.List;

import org.platanus.platachat.web.chat.model.MessagePayload;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
	List<MessagePayload> getChatLogFromSimpleChat(String roomId);
	
	MessagePayload saveMessage(MessagePayload message);
}
