package org.platanus.platachat.message.broker.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.broker.ListenerService;
import org.platanus.platachat.message.chat.dto.MessageDto;
import org.platanus.platachat.message.constant.SimpleConfigConstant;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerAdaptor implements ListenerService {

    @Override
    @KafkaListener(topics = SimpleConfigConstant.TOPIC_NAME, groupId = SimpleConfigConstant.GROUP_ID)
    public String simpleListener(String message) {
        log.info(message);
        return message;
    }

    @Override
    public String dtoListener(MessageDto dto) {
        return null;
    }
}
