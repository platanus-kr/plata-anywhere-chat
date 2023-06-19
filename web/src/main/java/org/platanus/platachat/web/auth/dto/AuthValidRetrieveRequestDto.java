package org.platanus.platachat.web.auth.dto;

import lombok.*;

/**
 * 회원 인증 요청 DTO
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthValidRetrieveRequestDto {

    /**
     * 인증요청 Session ID (Spring Session)
     */
    private String sessionId;

    /**
     * 입장 하고자 하는 채팅방 ID
     */
    private String roomId;
}
