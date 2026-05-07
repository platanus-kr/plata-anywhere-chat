package org.platanus.platachat.message.auth.service;


import org.platanus.platachat.message.auth.dto.AuthServiceValidationResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<AuthServiceValidationResponse> getSessionHealth(final String accessToken, final String roomId);
}
