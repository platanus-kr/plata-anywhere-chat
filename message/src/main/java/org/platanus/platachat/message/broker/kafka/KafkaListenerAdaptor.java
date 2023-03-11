package org.platanus.platachat.message.broker.kafka;

import org.platanus.platachat.message.broker.ListenerService;
import org.platanus.platachat.message.chat.dto.MessageDto;
import org.platanus.platachat.message.constant.SimpleConfigConstant;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaListenerAdaptor implements ListenerService {

    @Override
    @KafkaListener(topics = SimpleConfigConstant.TOPIC_NAME, groupId = SimpleConfigConstant.GROUP_ID)
    public String simpleListener(String message) {
        return null;
    }

    @Override
    @KafkaListener
    public String dtoListener(MessageDto dto) {
        return null;
    }
}
