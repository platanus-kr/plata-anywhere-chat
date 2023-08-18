package org.platanus.platachat.web.auth.exception;

import lombok.Getter;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveResponseDto;
import org.platanus.platachat.web.constants.AuthConstant;

@Getter
public class CustomAuthException extends RuntimeException {
    private AuthValidRetrieveResponseDto responseDto;

    public CustomAuthException(AuthValidRetrieveResponseDto responseDto) {
        this.responseDto = responseDto;
    }

    public CustomAuthException(Exception e) {
        this.responseDto = AuthValidRetrieveResponseDto.builder()
                .isAdmission(false)
                .isLogin(false)
                .nickname(null)
                .message(AuthConstant.AUTH_FAILED)
                .build();
    }
}
