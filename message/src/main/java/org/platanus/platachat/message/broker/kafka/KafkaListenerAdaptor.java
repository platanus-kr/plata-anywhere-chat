package org.platanus.platachat.message.broker.kafka;

import org.platanus.platachat.message.broker.ListenerService;
import org.platanus.platachat.message.chat.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerAdaptor implements ListenerService {
	@Override
	@KafkaListener
	public String simpleListener(String message) {
		return null;
	}
	
	@Override
	@KafkaListener
	public String dtoListener(MessageDto dto) {
		return null;
	}
}
