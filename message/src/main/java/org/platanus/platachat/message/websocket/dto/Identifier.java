package org.platanus.platachat.message.websocket.dto;

public record Identifier(
        String channel,
        String memberId,
        String nickname,
        String token
) {
}
