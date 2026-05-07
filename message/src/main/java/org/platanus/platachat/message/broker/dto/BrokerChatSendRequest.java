package org.platanus.platachat.message.broker.dto;

import org.platanus.platachat.message.websocket.dto.WebSocketMessageMetadata;

import java.time.LocalDateTime;

public record BrokerChatSendRequest(
        String roomId,
        String message,
        String memberId,
        String nickname,
        LocalDateTime created
) {
    private final static String SUBSCRIBE_MESSAGE = "%s님이 채팅방에 입장 했습니다.";
    private final static String LEAVE_MESSAGE = "%s님이 채팅방에서 퇴장 했습니다.";
    private final static String SYSTEM_NICKNAME = "SYSTEM";

    /**
     * 채팅방 입장 첫 메시지를 포함한 요청
     *
     * @param dto {@link WebSocketMessageMetadata}
     * @return {@link BrokerChatSendRequest}
     */
    public static BrokerChatSendRequest forSubscribeFrom(WebSocketMessageMetadata dto) {
        return new BrokerChatSendRequest(
                dto.roomId(),
                String.format(SUBSCRIBE_MESSAGE, dto.nickname()),
                dto.memberId(),
                SYSTEM_NICKNAME,
                LocalDateTime.now()
        );
    }

    /**
     * 채팅방 퇴장 메시지를 포함한 요청
     *
     * @param dto {@link WebSocketMessageMetadata}
     * @return {@link BrokerChatSendRequest}
     */
    public static BrokerChatSendRequest forLeaveFrom(WebSocketMessageMetadata dto) {
        return new BrokerChatSendRequest(
                dto.roomId(),
                String.format(LEAVE_MESSAGE, dto.nickname()),
                dto.memberId(),
                SYSTEM_NICKNAME,
                LocalDateTime.now()
        );
    }

    /**
     * 채팅 메시지 요청
     *
     * @param dto {@link WebSocketMessageMetadata}
     * @param message 채팅 메시지
     * @return {@link BrokerChatSendRequest}
     */
    public static BrokerChatSendRequest from(WebSocketMessageMetadata dto, String message) {
        return new BrokerChatSendRequest(
                dto.roomId(),
                message,
                dto.memberId(),
                dto.nickname(),
                LocalDateTime.now()
        );
    }
}
