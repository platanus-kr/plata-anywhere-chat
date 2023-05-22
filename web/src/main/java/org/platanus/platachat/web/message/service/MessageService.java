package org.platanus.platachat.web.message.service;

import java.util.List;

import org.platanus.platachat.web.message.model.MessagePayload;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
	List<MessagePayload> getChatLogs(String roomId);
	
	Page<MessagePayload> getChatLogsByPaging(String roomId, int page);
	
	Page<MessagePayload> getChatLogsByPaging(String roomId, int page, int offset);
	
	MessagePayload saveMessage(MessagePayload message);
}
