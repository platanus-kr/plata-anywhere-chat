package org.platanus.platachat.message.websocket.roommessage;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.auth.service.AuthService;
import org.platanus.platachat.message.message.model.MessagePayload;
import org.platanus.platachat.message.message.repository.MessageRepository;
import org.platanus.platachat.message.utils.XSSFilter;
import org.platanus.platachat.message.websocket.broadcaster.MessageBroadcaster;
import org.platanus.platachat.message.websocket.dto.CommandType;
import org.platanus.platachat.message.websocket.dto.IdentifierDto;
import org.platanus.platachat.message.websocket.dto.WebSocketRequestDto;
import org.platanus.platachat.message.websocket.dto.WebSocketSubscribeDto;
import org.platanus.platachat.message.websocket.subscription.SubscriptionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 메시지 처리
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageWebSocketHandler implements WebSocketHandler {

    private final SubscriptionManager subscriptionManager;
    private final MessageBroadcaster messageBroadcaster;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final AuthService authService;
    private final MessageRepository messagesRepository;


    /**
     * <h3>메시지 처리를 위한 핸들러</h3>
     *
     * HandlerMapping 으로 부터 전달된 세션을 처리한다. <br />
     * 크게 메시지 처리 부분과 연결 해제시 구독 해제 부분으로 나뉜다.
     *
     * @param session {@link WebSocketSession} 웹소켓 세션
     * @return 메시지 처리 후 Mono<Void>
     */
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        AtomicReference<WebSocketSubscribeDto> channelSub = new AtomicReference<>();
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(payload -> handleMessage(payload, session, channelSub))
                .doFinally(signalType -> handleDisconnection(signalType, channelSub, session))
                .then();
    }

    /**
     * <h3>메시지 처리</h3>
     *
     * Stub 내 command로 분기하여 메시지를 처리한다. <br />
     * subscribe 는 {@link SubscriptionManager} 에 구독을 추가하고, <br />
     * message 는 {@link MessageBroadcaster} 로 메시지를 전달한다.
     *
     * @param payload    메시지 Payload
     * @param session    {@link WebSocketSession} 웹소켓 세션
     * @param atomicSubscribeDto {@link AtomicReference} 된 {@link WebSocketSubscribeDto}
     * @return 메시지 처리 후 Mono<Void>
     */
    private Mono<Void> handleMessage(String payload,
                                     WebSocketSession session,
                                     AtomicReference<WebSocketSubscribeDto> atomicSubscribeDto) {
        try {
            WebSocketRequestDto webSocketRequestDto = objectMapper.readValue(payload, WebSocketRequestDto.class);
            CommandType command = webSocketRequestDto.getCommand();
            IdentifierDto identifier = webSocketRequestDto.getIdentifier();
            WebSocketSubscribeDto subscribeDto = WebSocketSubscribeDto.builder()
                    .roomId(identifier.getChannel())
                    .memberId(identifier.getMemberId())
                    .nickname(identifier.getNickname())
                    .sessionId(identifier.getToken())
                    .build();
            atomicSubscribeDto.set(subscribeDto);

            if (command.equals(CommandType.SUBSCRIBE)) { // 여기부터 작업바람
                authService.getSessionHealth(subscribeDto.getSessionId(), subscribeDto.getRoomId())
                        // 채팅방 구현하면서 다시 손볼것.
                        .onErrorResume(error -> {
                            throw new IllegalArgumentException(error.getMessage());
                        })
                        .subscribeOn(Schedulers.boundedElastic())
                        .subscribe(response -> {
                            if (!response.getIsAdmission() || !response.getIsLogin()) {
                                throw new IllegalArgumentException(response.getMessage());
                            }
                        });
                return processSubscribeCommand(subscribeDto, session);
            } else if (command.equals(CommandType.MESSAGE)) {
                if (webSocketRequestDto.getMessage().length() < 1) {
                    return Mono.empty();
                }
                return processMessageCommand(subscribeDto, webSocketRequestDto.getMessage());
            }
        } catch (IOException e) {
            log.error("Error parsing WebSocket message", e);
        } catch (IllegalArgumentException e) {
            log.error("Error working WebSocket message", e);
            throw new IllegalArgumentException(e.getMessage());
        }
        return Mono.error(new IllegalArgumentException("Invalid WebSocket message"));
    }

    /**
     * <h3>구독 처리</h3>
     *
     * @param subscribeDto {@link WebSocketSubscribeDto}
     * @param session      {@link WebSocketSession} 웹 소켓 세션
     * @return 구독 추가 후 Mono.empty()
     */
    private Mono<Void> processSubscribeCommand(WebSocketSubscribeDto subscribeDto, WebSocketSession session) {
        subscriptionManager.addSubscription(subscribeDto.getRoomId(), session);
        log.info(subscribeDto.getRoomId() + " 채널에 " + subscribeDto.getNickname() + " 님이 입장하셨습니다.");
        messageBroadcaster.broadcastMessageToSubscribers(subscribeDto.getRoomId(), "SYSTEM", subscribeDto.getNickname() + "님이 채팅방에 입장 했습니다.");
        return Mono.empty();
    }

    /**
     * <h3>메시지 처리</h3>
     *
     * @param subscribeDto {@link WebSocketSubscribeDto}
     * @param messageText  채팅방에 전달하고자 하는 메시지
     * @return 메시지 브로드캐스트 후 Mono.empty()
     */
    private Mono<Void> processMessageCommand(WebSocketSubscribeDto subscribeDto, String messageText) {
        String message = XSSFilter.filterXSS(messageText);

        // 수신된 메시지를 저장.
        messagesRepository.save(MessagePayload.builder()
                .roomId(subscribeDto.getRoomId())
                .userId(subscribeDto.getMemberId())
                .nickname(subscribeDto.getNickname())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build()).subscribe();

        // 채팅방에 있는 모든 사용자에게 메시지를 전달.
        messageBroadcaster.broadcastMessageToSubscribers(subscribeDto.getRoomId(), subscribeDto.getNickname(), message);
        return Mono.empty();
    }

    /**
     * <h3>연결 해제 처리</h3>
     * 웹소켓 연결이 해제 되었을때 구독을 해지하는 이유는 크게 3가지로 보인다. <br />
     * <ul>
     *     <li>리소스 낭비 방지 : 연결을 해제 함으로써 메모리 사용량 줄이기 (이걸로..?)</li>
     *     <li>연결되지 않은 소켓에 전송 방지 : 연결이 종료된 소켓에 메시지 전송을 시도하면 에러 발생. 해제를 통해 이를 1차적으로 방지한다.</li>
     *     <li>실시간 구독자 정보 유지 : 사용자들이 채팅방에 참여하거나 떠나는것을 실시간으로 반영하고 구독자 수를 정확하게 표시한다.</li>
     * </ul>
     *
     * @param signalType {@link SignalType} 리엑티브 스트림의 시그널 타입
     * @param channelSubRef {@link AtomicReference} 된 {@link WebSocketSubscribeDto}
     * @param session    {@link WebSocketSession} 웹 소켓 세션
     */
    private void handleDisconnection(SignalType signalType,
                                     AtomicReference<WebSocketSubscribeDto> channelSubRef,
                                     WebSocketSession session) {
        if (SignalType.ON_COMPLETE.equals(signalType) || SignalType.ON_ERROR.equals(signalType)) {
            WebSocketSubscribeDto channelSub = channelSubRef.get();
            if (channelSub == null) return;
            messageBroadcaster.broadcastMessageToSubscribers(channelSub.getRoomId(),
                    "SYSTEM", channelSub.getNickname() + "가 퇴장합니다.");
            subscriptionManager.removeSubscription(channelSub.getRoomId(), session);
        }
    }
}
