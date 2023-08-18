package org.platanus.platachat.message.broker.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.broker.dto.BrokerChatMessage;
import org.platanus.platachat.message.websocket.broadcaster.MessageBroadcaster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Profile({"kafka", "production"})
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaChatConsumerAdaptor {

    private final MessageBroadcaster messageBroadcaster;


    @KafkaListener(topics = "${plataanywherechat.broker.chat.message.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "chatMessageListener")
    public void consume(BrokerChatMessage chatMessage) {
        log.info("Consume raw : " + chatMessage);
        messageBroadcaster.broadcastMessageToSubscribers(chatMessage.getRoomId(), chatMessage.getNickname(), chatMessage.getMessage());
    }
}