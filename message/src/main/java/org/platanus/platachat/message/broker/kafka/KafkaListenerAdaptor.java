package org.platanus.platachat.message.broker.kafka;

import org.platanus.platachat.message.broker.ListenerService;
import org.platanus.platachat.message.broker.dto.BrokerRequestDto;
import org.platanus.platachat.message.contants.KafkaSimpleConfigConstant;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Deprecated
@Slf4j
//@Component
//@RequiredArgsConstructor
public class KafkaListenerAdaptor implements ListenerService {
	
	@Override
	@KafkaListener(topics = KafkaSimpleConfigConstant.TOPIC_NAME, groupId = KafkaSimpleConfigConstant.GROUP_ID)
	public String simpleListener(String message) {
		log.info(message);
		return message;
	}
	
	@Override
	public String dtoListener(BrokerRequestDto dto) {
		return null;
	}
}