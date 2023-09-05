package org.platanus.platachat.message.broker.dto;

import lombok.Builder;
import lombok.Getter;
import org.platanus.platachat.message.websocket.dto.WebSocketMessageMetadata;

import java.time.LocalDateTime;

@Getter
@Builder
public class BrokerChatSendRequest {
    private final static String SUBSCRIBE_MESSAGE = "%s님이 채팅방에 입장 했습니다.";
    private final static String LEAVE_MESSAGE = "%s님이 채팅방에서 퇴장 했습니다.";
    private final static String SYSTEM_NICKNAME = "SYSTEM";
    /**
     * 채팅방 식별자
     */
    private String roomId;

    /**
     * 전송 메시지
     */
    private String message;

    /**
     * 회원 식별자
     */
    private String memberId;

    /**
     * 채널에서 사용하기 위한 닉네임
     */
    private String nickname;

    private LocalDateTime created;

    /**
     * 채팅방 입장 첫 메시지를 포함한 요청
     *
     * @param dto {@link WebSocketMessageMetadata}
     * @return {@link BrokerChatSendRequest}
     */
    public static BrokerChatSendRequest forSubscribeFrom(WebSocketMessageMetadata dto) {
        return BrokerChatSendRequest.builder()
                .roomId(dto.getRoomId())
                .message(String.format(SUBSCRIBE_MESSAGE, dto.getNickname()))
                .memberId(dto.getMemberId())
                .nickname(dto.getNickname())
                .nickname(SYSTEM_NICKNAME)
                .created(LocalDateTime.now())
                .build();
    }

    /**
     * 채팅방 퇴장 메시지를 포함한 요청
     *
     * @param dto {@link WebSocketMessageMetadata}
     * @return {@link BrokerChatSendRequest}
     */
    public static BrokerChatSendRequest forLeaveFrom(WebSocketMessageMetadata dto) {
        return BrokerChatSendRequest.builder()
                .roomId(dto.getRoomId())
                .message(String.format(LEAVE_MESSAGE, dto.getNickname()))
                .memberId(dto.getMemberId())
                .nickname(SYSTEM_NICKNAME)
                .created(LocalDateTime.now())
                .build();
    }

    /**
     * 채팅 메시지 요청
     *
     * @param dto {@link WebSocketMessageMetadata}
     * @param message 채팅 메시지
     * @return {@link BrokerChatSendRequest}
     */
    public static BrokerChatSendRequest from(WebSocketMessageMetadata dto, String message) {
        return BrokerChatSendRequest.builder()
                .roomId(dto.getRoomId())
                .message(message)
                .memberId(dto.getMemberId())
                .nickname(dto.getNickname())
                .created(LocalDateTime.now())
                .build();
    }
}
