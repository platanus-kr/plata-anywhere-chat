package org.platanus.platachat.message.websocket.subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.websocket.broadcaster.MessageFlux;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 채팅방 구독(입장) 관리
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionManager {

//    private final BrokerService brokerService;

    private final MessageFlux messageFlux;

    /**
     * <h3>구독 정보</h3>
     * 구독 정보를 저장하기 위한 Map 이다.<br />
     * <ol>
     *     <li>키 : 채팅방 식별자</li>
     *     <li>값 : 세션 (웹소켓 세션)</li>
     * </ol>
     */
    private final Map<String, Set<WebSocketSession>> subscriptions = new ConcurrentHashMap<>();

    /**
     * <h3>채널 구독하기</h3>
     * 채널을 구독하는데 필요한 정보<br />
     * <ol>
     *     <li>채팅방 식별자</li>
     *     <li>세션 아이디 (웹소켓 세션 아이디)</li>
     * </ol>
     * 세션 구독 정보를 <code>subscriptions</code>에 저장한다. <code>ConcurrentHashMap</code> 에 저장.<br />
     * 구독후 채널에 구독 메시지 브로드케스트.<br />
     *
     * @param channel 구독 하고자 하는 채팅방 식별자
     * @param session 구독 하고자 하는 {@link WebSocketSession} 웹 소켓 세션
     */
    public void addSubscription(String channel, WebSocketSession session) {
//        String uniqueKey = messageFlux.getChannelUniqueKey(channel, session);
        subscriptions.computeIfAbsent(channel, key -> new CopyOnWriteArraySet<>()).add(session);
        Flux<WebSocketMessage> flux = Flux.create(sink -> messageFlux.addSink(channel, session, sink));
//        brokerService.sendSubscription(channel);
        session.send(flux).doOnTerminate(() -> messageFlux.removeSink(channel, session)).subscribe();
    }

    /**
     * <h3>구독자 정보 획득</h3>
     *
     * @param channel 채팅방 식별자
     * @return 채팅방을 구독중인 {@link WebSocketSession} 웹 소켓 세션 Set
     */
    public Set<WebSocketSession> getSubscriptions(String channel) {
        return subscriptions.getOrDefault(channel, Collections.emptySet());
    }

    /**
     * <h3>구독 해제</h3>
     *
     * @param channel 채팅방 식별자
     * @param session 채팅방을 구독중인 {@link WebSocketSession} 웹 소켓 세
     */
    public void removeSubscription(String channel, WebSocketSession session) {
        if (subscriptions.containsKey(channel)) {
            subscriptions.get(channel).remove(session);
            if (subscriptions.get(channel).isEmpty()) {
                subscriptions.remove(channel);
            }
        }
    }

    public void removeSession(WebSocketSession session) {
        if (subscriptions.containsValue(session)) {
        }
    }
}
