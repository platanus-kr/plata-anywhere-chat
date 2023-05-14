package org.platanus.platachat.message.auth.service;


import org.platanus.platachat.message.auth.dto.AuthValidRetrieveResponseDto;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<AuthValidRetrieveResponseDto> getSessionHealth(final String sessionId, final String roomId);
}
