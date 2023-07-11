package org.platanus.platachat.message.broker.kafka;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.message.broker.ProducerComponent;
import org.platanus.platachat.message.broker.dto.BrokerMessageDto;
import org.platanus.platachat.message.broker.dto.BrokerSendRequestDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPublishAdaptor implements ProducerComponent {

	private static final String MESSAGE_TOPIC = "production.pac.chat.message";
	
	private final KafkaTemplate<String, BrokerMessageDto> kafkaTemplate;

	@Override
	public void sendMessage(BrokerSendRequestDto request) {
		final String channel = request.getChannel();
		BrokerMessageDto message = BrokerMessageDto.from(request);
		kafkaTemplate.send(MESSAGE_TOPIC, channel, message);
	}


//	@Override
//	public void simpleSendMessage(String message) {
//		ListenableFuture<SendResult<String, String>> future =
//				kafkaTemplate.send(KafkaSimpleConfigConstant.TOPIC_NAME, message);
//		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//			@Override
//			public void onSuccess(SendResult<String, String> result) {
//				System.out.println("Sent message=[" + message +
//						"] with offset=[" + result.getRecordMetadata().offset() + "]");
//			}
//
//			@Override
//			public void onFailure(Throwable ex) {
//				System.out.println("Unable to send message=["
//						+ message + "] due to : " + ex.getMessage());
//			}
//		});
//	}
}
