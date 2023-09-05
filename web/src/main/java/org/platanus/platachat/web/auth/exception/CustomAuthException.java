package org.platanus.platachat.web.auth.exception;

import lombok.Getter;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveResponse;
import org.platanus.platachat.web.constants.AuthConstant;

@Getter
public class CustomAuthException extends RuntimeException {
    private AuthValidRetrieveResponse responseDto;

    public CustomAuthException(AuthValidRetrieveResponse responseDto) {
        this.responseDto = responseDto;
    }

    public CustomAuthException(Exception e) {
        this.responseDto = AuthValidRetrieveResponse.builder()
                .isAdmission(false)
                .isLogin(false)
                .nickname(null)
                .message(AuthConstant.AUTH_FAILED)
                .build();
    }
}
