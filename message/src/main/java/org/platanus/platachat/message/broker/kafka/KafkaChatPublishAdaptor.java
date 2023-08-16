package org.platanus.platachat.message.broker.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.broker.dto.BrokerChatMessage;
import org.platanus.platachat.message.broker.dto.BrokerChatSendRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Profile({"kafka", "production"})
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaChatPublishAdaptor {

    @Value("${plataanywherechat.broker.chat.message.topic}")
    private String chatMessageTopic;

    private final KafkaTemplate<String, BrokerChatMessage> chatMessageKafkaTemplate;

    public void sendMessage(BrokerChatSendRequest request) {
        final String channel = request.getChannel();
        BrokerChatMessage message = BrokerChatMessage.from(request);
        chatMessageKafkaTemplate.send(chatMessageTopic, channel, message); // TODO : 이거 key 를 왜 추가 해놨을까..
    }
}
