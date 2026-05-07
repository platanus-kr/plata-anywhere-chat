package org.platanus.platachat.web.chat.exception;

import org.platanus.platachat.web.chat.dto.ChatExceptionResponse;
import org.platanus.platachat.web.constants.AuthConstant;

public class CustomChatException extends RuntimeException {
    private ChatExceptionResponse responseDto;

    public CustomChatException(ChatExceptionResponse responseDto) {
        this.responseDto = responseDto;
    }

    public CustomChatException(Exception e) {
        this.responseDto = new ChatExceptionResponse(
                403L,
                AuthConstant.AUTH_FAILED
        );
    }

    public ChatExceptionResponse getResponseDto() {
        return responseDto;
    }
}
