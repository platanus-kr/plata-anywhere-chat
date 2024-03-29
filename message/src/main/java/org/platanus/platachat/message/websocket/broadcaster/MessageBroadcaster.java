package org.platanus.platachat.message.websocket.broadcaster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.websocket.dto.Identifier;
import org.platanus.platachat.message.websocket.dto.WebSocketResponse;
import org.platanus.platachat.message.websocket.subscription.SubscriptionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.FluxSink;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 채팅방 내 모든 구독자에게 메시지 송신
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageBroadcaster {

    private final MessageFlux messageFlux;
    private final SubscriptionManager subscriptionManager;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static String formatHHMMSS(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateTime.format(formatter);
    }

    /**
     * <h3>구독자에게 메시지 전송하기</h3>
     * 특정 채팅방에 있는 모든 구독자에게 메시지를 전송한다. <br />
     *
     * @param channel  메시지를 전송할 채팅방 식별자
     * @param nickname 메시지를 전송하는 사용자의 닉네임
     * @param message  전송할 메시지
     */
    public void broadcastMessageToSubscribers(String channel, String nickname, String message) {
        subscriptionManager.getSubscriptions(channel).forEach(session -> {
            if (session.isOpen()) {
                String uniqueKey = messageFlux.getChannelUniqueKey(channel, session);
                FluxSink<WebSocketMessage> sink = messageFlux.getSink(uniqueKey);
                if (sink != null) {
                    WebSocketResponse webSocketResponse = WebSocketResponse.builder()
                            .command("broadcast")
                            .message(message)
                            .timestamp(formatHHMMSS(LocalDateTime.now()))
                            .identifier(Identifier.builder()
                                    .channel(channel)
                                    .nickname(nickname)
                                    .build())
                            .build();
                    try {
                        String payload = objectMapper.writeValueAsString(webSocketResponse);
                        //brokerService.sendChatMessage(channel, message);
                        sink.next(session.textMessage(payload));
                    } catch (JsonProcessingException e) {
                        log.error("Error serializing message to JSON", e);
                    }
                }
            } else {
                subscriptionManager.removeSubscription(channel, session);
                messageFlux.removeSink(channel, session);
            }
        });
    }
}