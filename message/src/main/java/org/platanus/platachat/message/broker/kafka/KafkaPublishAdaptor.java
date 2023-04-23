package org.platanus.platachat.message.broker.kafka;

import org.platanus.platachat.message.broker.PublishService;
import org.platanus.platachat.message.contants.SimpleConfigConstant;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Deprecated
@Slf4j
//@Component
//@RequiredArgsConstructor
public class KafkaPublishAdaptor implements PublishService {
	
	private KafkaTemplate<String, String> kafkaTemplate;
	

	@Override
	public void simpleSendMessage(String message) {
		ListenableFuture<SendResult<String, String>> future =
				kafkaTemplate.send(SimpleConfigConstant.TOPIC_NAME, message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				System.out.println("Sent message=[" + message +
						"] with offset=[" + result.getRecordMetadata().offset() + "]");
			}
			
			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Unable to send message=["
						+ message + "] due to : " + ex.getMessage());
			}
		});
	}
}
