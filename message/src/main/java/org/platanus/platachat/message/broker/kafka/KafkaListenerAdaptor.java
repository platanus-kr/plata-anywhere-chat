package org.platanus.platachat.message.broker.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.broker.ListenerService;
import org.platanus.platachat.message.chat.dto.BrokerRequestDto;
import org.platanus.platachat.message.contants.SimpleConfigConstant;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Deprecated
@Slf4j
//@Component
//@RequiredArgsConstructor
public class KafkaListenerAdaptor implements ListenerService {

    @Override
    @KafkaListener(topics = SimpleConfigConstant.TOPIC_NAME, groupId = SimpleConfigConstant.GROUP_ID)
    public String simpleListener(String message) {
        log.info(message);
        return message;
    }

    @Override
    public String dtoListener(BrokerRequestDto dto) {
        return null;
    }
}