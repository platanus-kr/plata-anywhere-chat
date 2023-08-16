package org.platanus.platachat.message.broker.kafka;

import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.broker.dto.BrokerChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Profile({"kafka", "production"})
@Slf4j
@Component
public class KafkaChatConsumerAdaptor {

    @KafkaListener(topics = "${plataanywherechat.broker.chat.message.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "chatMessageListener")
    public void consume(BrokerChatMessage message) {
        log.info("Consume raw : " + message); // TODO :  구현
    }
}