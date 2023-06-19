package org.platanus.platachat.message.websocket.broadcaster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 채팅방과 WebSocket 세션을 관리
 */
@Slf4j
@Component
public class MessageFlux {

    /**
     * <h3>채팅방을 관리하는 Flux</h3>
     * 클라이언트와 연결된 웹소켓을 관리하는 FluxSink를 가지는 Map 이다. <br />
     * <ol>
     *     <li>키 : 채팅방 식별자와 웹소켓 세션 아이디의 조합</li>
     *     <li>값 : {@link FluxSink} ({@link WebSocketMessage}를 처리하는 FluxSink)</li>
     * </ol>
     */
    private final Map<String, FluxSink<WebSocketMessage>> sinks = new ConcurrentHashMap<>();

    public void addSink(String channel, WebSocketSession session, FluxSink<WebSocketMessage> sink) {
        String uniqueKey = getChannelUniqueKey(channel, session);
        sinks.put(uniqueKey, sink);
    }

    public FluxSink<WebSocketMessage> getSink(String uniqueKey) {
        return sinks.get(uniqueKey);
    }

    public void removeSink(String channel, WebSocketSession session) {
        String uniqueKey = getChannelUniqueKey(channel, session);
        sinks.remove(uniqueKey);
    }

    public String getChannelUniqueKey(String channel, WebSocketSession session) {
        return channel + "-" + session.getId();
    }

}
