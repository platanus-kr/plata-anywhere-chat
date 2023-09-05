package org.platanus.platachat.message.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원 인증 응답 DTO
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthValidRetrieveResponse {

    /**
     * 회원 실제 닉네임
     */
    private String nickname;

    /**
     * 채팅방 입장 가능 여부
     */
    private Boolean isAdmission;

    /**
     * 세션 유효성 여부, 로그인 여부 (Authentication.isAuthenticated)
     */
    private Boolean isLogin;

    /**
     * 응답 메시지
     */
    private String message;
}
