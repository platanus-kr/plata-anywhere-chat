package org.platanus.platachat.message.websocket.dto;

public record WebSocketResponse(
        String command,
        String message,
        String timestamp,
        Identifier identifier
) {
}
