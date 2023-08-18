package org.platanus.platachat.message.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentifierDto {
    /**
     * 채팅방 식별자 (변경하면안됨)
     */
    private String channel;

    /**
     * 회원 식별자
     */
    private String memberId;

    /**
     * 회원의 현재 닉네임
     */
    private String nickname;

    /**
     * 인증을 위한 스프링 세션 ID
     */
    private String token;

}
