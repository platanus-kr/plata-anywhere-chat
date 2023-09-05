package org.platanus.platachat.message.utils;

import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.message.auth.dto.AuthValidRetrieveResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class AuthenticationController {

    /**
     * 테스트용 세션 유효성 검사 Fake API
     *
     * @return {@link AuthValidRetrieveResponse}
     */
    @PostMapping("/api/v1/auth/validate")
    public Mono<AuthValidRetrieveResponse> validate() {
        log.info("Fake 인가 진입");
        AuthValidRetrieveResponse responseDto = AuthValidRetrieveResponse.builder()
                .isAdmission(true)
                .isLogin(true)
                .nickname("TEST_NICKNAME")
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
        return Mono.just(responseDto);
    }
}
