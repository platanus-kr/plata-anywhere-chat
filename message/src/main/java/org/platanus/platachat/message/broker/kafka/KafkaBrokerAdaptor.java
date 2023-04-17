package org.platanus.platachat.message.broker.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.platanus.platachat.message.broker.BrokerService;
import org.platanus.platachat.message.websocket.MessageBroadcaster;
import org.platanus.platachat.message.websocket.SubscriptionManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaBrokerAdaptor implements BrokerService {
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final SubscriptionManager subscriptionManager;
	private final MessageBroadcaster messageBroadcaster;
	
	@Override
	public void sendSubscription(String topic) {
	
	}
	
	@Override
	public void sendChatMessage(String topic, String message) {
		kafkaTemplate.send(topic, message);
	}
	
	@Override
	public void receiveChatMessage(ConsumerRecord<String, String> record) {
		String message = record.value();
		//messageBroadcaster.broadcastMessageToSubscribers(message);
	}
}
