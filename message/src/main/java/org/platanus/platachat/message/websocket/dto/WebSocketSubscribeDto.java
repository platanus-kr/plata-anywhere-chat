package org.platanus.platachat.message.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * <h2>채팅방 구독을 위한 구독 DTO</h2>
 * 원자성이 보장되야 하는 객체다.
 * 이는 Record 타입으로도 불변 객체를 만들 수 있다. (since Java 14)
 */
@Getter
@Builder
@AllArgsConstructor
public class WebSocketSubscribeDto {
    /**
     * 구독하고자 하는 채팅방 식별자
     */
    private final String roomId;

    /**
     * 채널에서 사용하기 위한 닉네임
     */
    private final String nickname;
    
    /**
     * 회원 식별자
     */
    private final String memberId;
    
    /**
     * 채널에서 사용하기 위한 세션
     */
    private final String sessionId;
}
