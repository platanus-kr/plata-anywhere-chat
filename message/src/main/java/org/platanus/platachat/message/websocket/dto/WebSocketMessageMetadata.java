package org.platanus.platachat.message.websocket.dto;

/**
 * <h2>채팅 메시지 메타데이터 DTO</h2>
 * 원자성이 보장되야 하는 객체다.
 * 이는 Record 타입으로도 불변 객체를 만들 수 있다. (since Java 14)
 */
public record WebSocketMessageMetadata(
        String roomId,
        String memberId,
        String nickname,
        String accessToken
) {
}
