package org.platanus.platachat.message.websocket.acceptance;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.platanus.platachat.message.utils.MessageSpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("간단 메시지 전송 인수 테스트")
public class SimpleMessageWebSocketAcceptanceTest extends MessageSpringBootTest {

    @LocalServerPort
    private Integer port;

    private URI uri;

    private ReactorNettyWebSocketClient webSocketClient;

    @BeforeEach
    public void setup() {
        this.webSocketClient = new ReactorNettyWebSocketClient();
        this.uri = URI.create("ws://localhost:" + port + "/simplemessage");
    }

    @DisplayName("웹소켓 연결 테스트")
    @Test
    public void establishConnectWebSocketTest() {
        // given
        String sendMessage = getSubscribeRequest();

        webSocketClient.execute(uri, session -> {
                    WebSocketMessage webSocketMessage = session.textMessage(sendMessage);

                    // when
                    return session.send(Mono.just(webSocketMessage))
                            .thenMany(session.receive().take(1))
                            .doOnNext(message -> {
                                String payload = message.getPayloadAsText();
                                JSONObject jsonObject = getJsonObject(payload);
                                // {"command":"broadcast","message":"TEST1님이 채팅방에 입장 했습니다.","timestamp":"15:51:45","identifier":{"channel":"TEST_CHANNEL","memberId":null,"nickname":"SYSTEM","token":null}}

                                // then
                                assertEquals("broadcast", getStringFromJsonObject(jsonObject, "command"));
                            }).then();
                })
                .block(Duration.ofSeconds(5));
    }

    @DisplayName("메시지 전송 테스트")
    @Test
    public void sendMessageWebsocketTest() {
        // Given: 구독 요청 메시지
        String subscribeMessage = getSubscribeRequest();
        String sendMessage = getMessageRequest();

        // Step 1: 채팅방 구독 후 메시지 전송
        webSocketClient.execute(uri, session -> {
                    WebSocketMessage webSocketSubscribeMessage = session.textMessage(subscribeMessage);
                    WebSocketMessage webSocketMessage = session.textMessage(sendMessage);

                    Mono<Void> subscribeMono = session.send(Mono.just(webSocketSubscribeMessage))
                            .thenMany(session.receive().take(1))
                            .doOnNext(subscribeResponse -> {
                                String payload = subscribeResponse.getPayloadAsText();
                                JSONObject jsonObject = getJsonObject(payload);
                                assertEquals("broadcast", getStringFromJsonObject(jsonObject, "command"));
                            }).then();

                    Mono<Void> sendMono = session.send(Mono.just(webSocketMessage))
                            .thenMany(session.receive().take(1))
                            .doOnNext(messageResponse -> {
                                String payload = messageResponse.getPayloadAsText();
                                JSONObject jsonObject = getJsonObject(payload);
                                assertEquals("안녕하세요", getStringFromJsonObject(jsonObject, "message"));
                            }).then();

                    return subscribeMono.then(sendMono);
                })
                .block(Duration.ofSeconds(5));
    }


    private String getSubscribeRequest() {
        return "{\"command\" : \"SUBSCRIBE\"," +
                "\"identifier\" : {\"channel\" : \"TEST_CHANNEL\" , \"nickname\" : \"TEST1\"}" +
                "}";
    }

    private String getMessageRequest() {
        return "{\"command\" : \"MESSAGE\"," +
                "\"message\" : \"안녕하세요\"," +
                "\"identifier\" : {\"channel\" : \"TEST_CHANNEL\" , \"nickname\" : \"TEST1\"}" +
                "}";
    }

    private Object getStringFromJsonObject(JSONObject jsonObject, final String path) {
        try {
            return jsonObject.get(path);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONObject getJsonObject(String payload) {
        try {
            return new JSONObject(payload);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
