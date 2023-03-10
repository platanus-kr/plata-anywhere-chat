package org.platanus.platachat.message.broker.kafka;

import org.platanus.platachat.message.broker.ListenerService;
import org.platanus.platachat.message.chat.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaListenerAdaptor implements ListenerService {
	
	private final KafkaTemplate<String, String> simpleKafkaTemplate;
	@Override
	@KafkaListener(topics = "chatroom")
	public void simpleListener(@Payload String message) {
		simpleKafkaTemplate.send("chatroom", message);
	}
	
	@Override
	@KafkaListener
	public String dtoListener(MessageDto dto) {
		return null;
	}
}
