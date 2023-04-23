package org.platanus.platachat.message.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MessageFlux {

    /**
     * <h4>채널을 관리하는 Flux</h4>
     * 클라이언트와 연결된 웹소켓을 관리하는 FluxSink를 가지는 Map 이다. <br />
     * <ol>
     *     <li>키 : 채널과 웹소켓 세션 아이디의 조합</li>
     *     <li>값 : FluxSink (WebSocketMessage를 처리하는 FluxSink)</li>
     * </ol>
     */
    private final Map<String, FluxSink<WebSocketMessage>> sinks = new ConcurrentHashMap<>();

    public void addSink(String channel, WebSocketSession session, FluxSink<WebSocketMessage> sink) {
        String uniqueKey = channel + "-" + session.getId();
        sinks.put(uniqueKey, sink);
    }

    public FluxSink<WebSocketMessage> getSink(String uniqueKey) {
        return sinks.get(uniqueKey);
    }

    public void removeSink(String channel, WebSocketSession session) {
        String uniqueKey = channel + "-" + session.getId();
        sinks.remove(uniqueKey);
    }

}
