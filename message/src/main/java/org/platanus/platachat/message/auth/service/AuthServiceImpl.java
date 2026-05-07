package org.platanus.platachat.message.auth.service;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.message.auth.dto.AuthServiceTokenClaims;
import org.platanus.platachat.message.auth.dto.AuthServiceValidationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * auth-service 프로토콜 기반 인증 서비스
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthServiceJwtVerifier jwtVerifier;

    /**
     * access token 유효성을 검사한다.
     *
     * @param accessToken 클라이언트로부터 전달된 access token
     * @param roomId      클라이언트가 입장하기 원하는 채팅방
     * @return 인증 및 입장 가능 여부
     */
    @Override
    public Mono<AuthServiceValidationResponse> getSessionHealth(final String accessToken,
                                                            final String roomId) {
        return Mono.fromSupplier(() -> {
            AuthServiceTokenClaims claims = jwtVerifier.verifyAccessToken(accessToken);
            return new AuthServiceValidationResponse(
                    claims.displayName(),
                    true,
                    true,
                    claims.userId()
            );
        });
    }
}
