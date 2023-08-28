package org.platanus.platachat.message.utils;

import org.platanus.platachat.message.auth.dto.AuthValidRetrieveResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    /**
     * 테스트용 세션 유효성 검사 Fake API
     *
     * @return {@link AuthValidRetrieveResponseDto}
     */
    @PostMapping("/api/v1/auth/validate")
    public AuthValidRetrieveResponseDto validate() {
        return AuthValidRetrieveResponseDto.builder()
                .isAdmission(true)
                .isLogin(true)
                .nickname("TEST_NICKNAME")
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
