package org.platanus.platachat.message.broker;

import org.platanus.platachat.message.chat.dto.MessageDto;

public interface ListenerService {
	// 리스너
	String simpleListener(String message);
	
	String dtoListener(MessageDto dto);
}
