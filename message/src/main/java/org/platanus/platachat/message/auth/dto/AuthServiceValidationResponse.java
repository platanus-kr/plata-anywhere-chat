package org.platanus.platachat.message.auth.dto;

/**
 * auth-service token 검증 응답 DTO
 */
public record AuthServiceValidationResponse(
        String nickname,
        Boolean isAdmission,
        Boolean authenticated,
        String message
) {
}
