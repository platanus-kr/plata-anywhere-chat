package org.platanus.platachat.message.websocket;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public interface MessageWebSocketHandler extends WebSocketHandler {
    Mono<Void> handle(WebSocketSession session);
}
