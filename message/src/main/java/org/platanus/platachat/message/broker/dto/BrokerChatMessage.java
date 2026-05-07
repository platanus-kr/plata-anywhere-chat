package org.platanus.platachat.message.broker.dto;

import java.time.LocalDateTime;

public record BrokerChatMessage(
        String roomId,
        String message,
        String memberId,
        String nickname,
        LocalDateTime created
) {

    public static BrokerChatMessage from(BrokerChatSendRequest request) {
        return new BrokerChatMessage(
                request.roomId(),
                request.message(),
                request.memberId(),
                request.nickname(),
                request.created()
        );
    }
}
