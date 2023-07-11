package org.platanus.platachat.message.broker.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BrokerSendRequestDto {
    private String channel;
    private String message;
    private String userId;
    private String nickname;
    private LocalDateTime created;
}
