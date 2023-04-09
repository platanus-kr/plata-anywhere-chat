package org.platanus.platachat.message.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionManager {

    private final MessageFlux messageFlux;

    /**
     * <h4>구독 정보</h4>
     * 구독 정보를 저장하기 위한 Map 이다.<br />
     * <ol>
     *     <li>키 : 채널명</li>
     *     <li>값 : 세션 (웹소켓 세션)</li>
     * </ol>
     */
    private final Map<String, Set<WebSocketSession>> subscriptions = new ConcurrentHashMap<>();

    /**
     * <h4>채널 구독하기</h4>
     * 채널을 구독하는데 필요한 정보<br />
     * <ol>
     *     <li>채널명</li>
     *     <li>세션 아이디 (웹소켓 세션 아이디)</li>
     * </ol>
     * 세션 구독 정보를 <code>subscriptions</code>에 저장한다. <code>ConcurrentHashMap</code> 에 저장.<br />
     * 구독후 채널에 구독 메시지 브로드케스트.<br />
     *
     * @param channel  구독 하고자 하는 채널명
     * @param session  구독 하고자 하는 세션
     */
    public void addSubscription(String channel, WebSocketSession session) {
        String uniqueKey = channel + "-" + session.getId();
        subscriptions.computeIfAbsent(channel, key -> new CopyOnWriteArraySet<>()).add(session);
        Flux<WebSocketMessage> flux = Flux.create(sink -> messageFlux.addSink(channel, session, sink));
        session.send(flux).doOnTerminate(() -> messageFlux.removeSink(channel, session)).subscribe();
    }

    public Set<WebSocketSession> getSubscriptions(String channel) {
        return subscriptions.getOrDefault(channel, Collections.emptySet());
    }

    public void removeSubscription(String channel, WebSocketSession session) {
        if (subscriptions.containsKey(channel)) {
            subscriptions.get(channel).remove(session);
            if (subscriptions.get(channel).isEmpty()) {
                subscriptions.remove(channel);
            }
        }
    }
}
