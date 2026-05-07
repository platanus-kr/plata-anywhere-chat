package org.platanus.platachat.message.websocket.dto;

import java.time.LocalDateTime;

public record WebSocketRequest(
        CommandType command,
        String message,
        LocalDateTime sendTime,
        Identifier identifier
) {
}
