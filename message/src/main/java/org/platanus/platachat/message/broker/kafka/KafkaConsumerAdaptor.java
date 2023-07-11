package org.platanus.platachat.message.broker.kafka;

import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.broker.ConsumerComponent;
import org.platanus.platachat.message.broker.dto.BrokerMessageDto;
import org.platanus.platachat.message.broker.dto.BrokerSendRequestDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerAdaptor implements ConsumerComponent {

    @Override
    public void consume(BrokerMessageDto request) {

    }
}