package org.platanus.platachat.web.chat.exception;

import lombok.Getter;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveResponse;
import org.platanus.platachat.web.constants.AuthConstant;

@Getter
public class CustomChatException extends RuntimeException {
    private AuthValidRetrieveResponse responseDto;

    public CustomChatException(AuthValidRetrieveResponse responseDto) {
        this.responseDto = responseDto;
    }

    public CustomChatException(Exception e) {
        this.responseDto = AuthValidRetrieveResponse.builder()
                .isAdmission(false)
                .isLogin(false)
                .nickname(null)
                .message(AuthConstant.AUTH_FAILED)
                .build();
    }
}
