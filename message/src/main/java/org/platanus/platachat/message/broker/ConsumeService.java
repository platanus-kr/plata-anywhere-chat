package org.platanus.platachat.message.broker;

import org.platanus.platachat.message.chat.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;

public interface ConsumeService {
	// 리스너
	String simpleListener(String message);
	
	String dtoListener(MessageDto dto);
}
