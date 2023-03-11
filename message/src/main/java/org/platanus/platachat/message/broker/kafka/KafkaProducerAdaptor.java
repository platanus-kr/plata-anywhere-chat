package org.platanus.platachat.message.broker.kafka;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.message.broker.PublishService;
import org.platanus.platachat.message.constant.SimpleConfigConstant;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
public class KafkaProducerAdaptor implements PublishService {

    private final KafkaTemplate<String, String> simpleKafkaTemplate;

    /**
     * 송신 API는 수신 대기 가능한 미래 객체를 반환합니다.
     * 보내는 스레드를 차단하고 보낸 메시지에 대한 결과를 얻으려면 ListenableFuture 객체의 get API를 호출할 수 있습니다.
     * 스레드는 결과를 기다리지 만 생산자 속도가 느려집니다.
     * Kafka는 빠른 스트림 처리 플랫폼입니다.
     * 따라서 후속 메시지가 이전 메시지의 결과를 기다리지 않도록 결과를 비동기적으로 처리하는 것이 좋습니다.
     * 콜백을 통해이 작업을 수행 할 수 있습니다.
     *
     * @param message
     */
    @Override
    public void simpleSendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future =
                simpleKafkaTemplate.send(SimpleConfigConstant.TOPIC_NAME, message);

        future.addCallback(new ListenableFutureCallback<>() {

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
