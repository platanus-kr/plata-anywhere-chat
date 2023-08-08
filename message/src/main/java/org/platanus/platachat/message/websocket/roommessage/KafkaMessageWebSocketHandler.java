package org.platanus.platachat.message.websocket.roommessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.websocket.MessageWebSocketHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * Kafka 메시지 브로커 백엔드를 사용하는 메시지 처리
 */
@Slf4j
@Component("messageWebSocketHandler")
@Profile({"kafka", "production"})
@RequiredArgsConstructor
public class KafkaMessageWebSocketHandler implements MessageWebSocketHandler {

    /**
     * <h3>메시지 처리를 위한 핸들러</h3>
     * <p>
     * HandlerMapping 으로 부터 전달된 세션을 처리한다. <br />
     * 크게 메시지 처리 부분과 연결 해제시 구독 해제 부분으로 나뉜다.
     *
     * @param session {@link WebSocketSession} 웹소켓 세션
     * @return 메시지 처리 후 Mono<Void>
     */
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return null; //TODO: 여기서부터 하세요.
    }


}
