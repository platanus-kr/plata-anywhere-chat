package org.platanus.platachat.message.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BrokerChatMessage {
    private String message;
    private String userId;
    private String nickname;
    private LocalDateTime created;

    public static BrokerChatMessage from(BrokerChatSendRequest request) {
        return BrokerChatMessage.builder()
                .message(request.getMessage())
                .created(request.getCreated())
                .nickname(request.getNickname())
                .userId(request.getUserId())
                .build();
    }
}
