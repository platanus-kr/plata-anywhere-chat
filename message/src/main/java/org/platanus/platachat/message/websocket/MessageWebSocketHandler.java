package org.platanus.platachat.message.websocket;

import org.platanus.platachat.message.websocket.dto.WebSocketSubscribeDto;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

public interface MessageWebSocketHandler extends WebSocketHandler {
    Mono<Void> handle(WebSocketSession session);
}
