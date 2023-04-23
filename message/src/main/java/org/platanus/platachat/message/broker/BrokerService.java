package org.platanus.platachat.message.broker;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface BrokerService {
	
	void sendSubscription(String topic);
	
	void sendChatMessage(String topic, String message);
	
	void receiveChatMessage(ConsumerRecord<String, String> record);
}
