package org.platanus.platachat.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomWebSocketHandler implements WebSocketHandler {

//    private final WebSocketHandler delegate;

    @Override
    public Mono<Void> handle(WebSocketSession session) {

//        log.info("Session opened: {}", session.getId());
//        return delegate.handle(session)
//                .doFinally(signal -> {
//                    log.info("Session closed: {}", session.getId());
//                });
//
//        return session.receive()
//                .map(WebSocketMessage::getPayloadAsText)
//                .doOnNext(message -> {
//                    // 메시지 수신 시 동작할 로직 구현
//                    log.info("Received message: " + message);
//                })
//                .then();
        return null;
    }
}
