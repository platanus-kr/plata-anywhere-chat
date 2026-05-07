package org.platanus.platachat.web.chat.dto;

/**
 * 채팅 컨트롤러 에러 응답
 */
public record ChatExceptionResponse(
        Long errorCode,
        String errorMessage
) {
}
