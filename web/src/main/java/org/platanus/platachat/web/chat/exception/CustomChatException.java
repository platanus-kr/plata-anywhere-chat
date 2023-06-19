package org.platanus.platachat.web.chat.exception;

import lombok.Getter;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveResponseDto;
import org.platanus.platachat.web.constants.AuthConstant;

@Getter
public class CustomChatException extends RuntimeException {

    //    private String message;
    private AuthValidRetrieveResponseDto responseDto;

    public CustomChatException(AuthValidRetrieveResponseDto responseDto) {
        this.responseDto = responseDto;
    }

    public CustomChatException(Exception e) {
        this.responseDto = AuthValidRetrieveResponseDto.builder()
                .isAdmission(false)
                .isLogin(false)
                .nickname(null)
                .message(AuthConstant.AUTH_FAILED)
                .build();
    }
}
