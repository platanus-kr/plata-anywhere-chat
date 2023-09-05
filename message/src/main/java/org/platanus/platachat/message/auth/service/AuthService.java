package org.platanus.platachat.message.auth.service;


import org.platanus.platachat.message.auth.dto.AuthValidRetrieveResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<AuthValidRetrieveResponse> getSessionHealth(final String sessionId, final String roomId);
}
