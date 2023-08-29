package org.platanus.platachat.message.auth.service;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.message.auth.dto.AuthValidRetrieveRequestDto;
import org.platanus.platachat.message.auth.dto.AuthValidRetrieveResponseDto;
import org.platanus.platachat.message.contants.AuthConstant;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 회원 관련 인증을 위한 REST API 서비스
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String PAC_WEB_HOSTNAME = "plataanywherechat.web.application.host";
    private static final String PAC_WEB_PORT = "plataanywherechat.web.application.port";
    private final Environment env;

    /**
     * 세션의 유효성 검사를 WEB WAS에 요청한다.
     * 조회 결과에는 실제 닉네임을 포함한다.
     *
     * @param sessionId 클라이언트로부터 전달된 sessionId
     * @param roomId    클라이언트가 입장하기 원하는 채팅방
     * @return 세션의 유효성 검사 결과
     */
    @Override
    public Mono<AuthValidRetrieveResponseDto> getSessionHealth(final String sessionId,
                                                               final String roomId) {
        final String webApplicationServer = env.getProperty(PAC_WEB_HOSTNAME) + ":" + env.getProperty(PAC_WEB_PORT);

        WebClient webClient = WebClient.builder()
                .baseUrl("http://" + webApplicationServer)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        AuthValidRetrieveRequestDto requestDto = AuthValidRetrieveRequestDto.builder()
                .sessionId(sessionId)
                .roomId(roomId)
                .build();

        // WebClient를 사용할 때 일반적인 패턴임.
        // WebClient는 리액티브 프로그래밍 모델을 사용하므로, 요청을 정의하는 코드와 그 요청을 실제로 실행하는 코드를 분리할 수 있음.
        // 예를 들어, 동일한 요청을 다양한 조건에 따라 다른 방식으로 처리하도록 구성하여 유연성을 증대시킬 수 있음.
        return webClient.post()
                .uri(AuthConstant.AUTH_VALIDATE_URI)
                .body(Mono.just(requestDto), AuthValidRetrieveRequestDto.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    // 일단 이부분은 채팅방 구현하면서 다시 하는걸로.
                    return Mono.error(new IllegalArgumentException());
                })
                .bodyToMono(AuthValidRetrieveResponseDto.class);
    }
}
