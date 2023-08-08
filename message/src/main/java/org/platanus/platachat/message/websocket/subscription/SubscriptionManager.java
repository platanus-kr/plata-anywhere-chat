package org.platanus.platachat.message.websocket.subscription;

import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Set;

public interface SubscriptionManager {
     void addSubscription(String channel, WebSocketSession session);
     Set<WebSocketSession> getSubscriptions(String channel);
    void removeSubscription(String channel, WebSocketSession session);
    long countRoomMemberInRoom(String channel);
    void removeSession(WebSocketSession session);
}
