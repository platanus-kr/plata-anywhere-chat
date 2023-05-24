package org.platanus.platachat.message.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 원자성이 보장되야 하는 객체다.
 * 이는 Record 타입으로도 불변 객체를 만들 수 있다. (since Java 14)
 */
@Getter
@Builder
@AllArgsConstructor
public class ChannelSubscribeDto {
    /**
     * 구독하고자 하는 채널명
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
