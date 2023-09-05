package org.platanus.platachat.message.broker.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrokerChatMessage {
    private String roomId;
    private String message;
    private String memberId;
    private String nickname;
    private LocalDateTime created;

    public static BrokerChatMessage from(BrokerChatSendRequest request) {
        return BrokerChatMessage.builder()
                .roomId(request.getRoomId())
                .message(request.getMessage())
                .created(request.getCreated())
                .nickname(request.getNickname())
                .memberId(request.getMemberId())
                .build();
    }
}
